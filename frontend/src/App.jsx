import { useEffect, useRef, useState } from 'react';
import { createGame, getGame, performAction, selectReward } from './api/gameApi';
import CharacterSelection from './components/CharacterSelection';
import BattleArena from './components/BattleArena';
import { CombatLog, GameOverPanel, RewardPanel } from './components/GamePanels';

const STORAGE_KEY = 'realm-of-java.game-id';

function resultEntries(result, turn) {
  if (!result) return [];
  if (!result.successful) return [{ type: 'muted', text: `${result.actorName} could not use ${result.actionName}.`, turn }];
  const entries = [];
  if (result.damageDealt > 0) entries.push({ type: result.criticalHit ? 'critical' : 'damage', text: `${result.actorName} used ${result.actionName} on ${result.targetName} for ${result.damageDealt} damage.`, turn });
  if (result.criticalHit) entries.push({ type: 'critical', text: 'Critical hit!', turn });
  if (result.healingDone > 0) entries.push({ type: 'healing', text: `${result.actorName} used ${result.actionName} and restored ${result.healingDone} health.`, turn });
  return entries;
}

export default function App() {
  const [playerName, setPlayerName] = useState('');
  const [selectedClass, setSelectedClass] = useState('WARRIOR');
  const [gameState, setGameState] = useState(null);
  const [combatLog, setCombatLog] = useState([]);
  const [loading, setLoading] = useState(false);
  const [restoring, setRestoring] = useState(true);
  const [error, setError] = useState('');
  const lastEnemy = useRef('');
  const turn = useRef(1);

  const addEntries = (entries) => setCombatLog((current) => [...current, ...entries.map((entry) => ({ ...entry, id: `${Date.now()}-${Math.random()}` }))]);

  useEffect(() => {
    const savedGameId = window.sessionStorage.getItem(STORAGE_KEY);
    if (!savedGameId) { setRestoring(false); return; }
    getGame(savedGameId).then((state) => {
      setGameState(state);
      lastEnemy.current = state.enemyName || '';
      addEntries([{ type: 'system', text: 'Active game restored. Earlier combat log entries are not stored by the server.', turn: '—' }]);
    }).catch(() => window.sessionStorage.removeItem(STORAGE_KEY)).finally(() => setRestoring(false));
  }, []);

  async function startGame(event) {
    event.preventDefault(); setLoading(true); setError('');
    try {
      const state = await createGame({ playerName: playerName.trim(), characterClass: selectedClass });
      setGameState(state); setCombatLog([]); turn.current = 1; lastEnemy.current = state.enemyName;
      window.sessionStorage.setItem(STORAGE_KEY, state.gameId);
      addEntries([{ type: 'system', text: `A ${state.enemyName} appears. The first battle begins.`, turn: '—' }]);
    } catch (requestError) { setError(requestError.message); } finally { setLoading(false); }
  }

  async function handleAction(action) {
    if (!gameState) return;
    setLoading(true); setError('');
    const enemyBeforeTurn = gameState.enemyName;
    try {
      const response = await performAction(gameState.gameId, action);
      const next = response.gameState;
      const entries = [...resultEntries(response.playerAction, turn.current), ...resultEntries(response.enemyAction, turn.current)];
      if (next.awaitingReward || (next.gameCompleted && next.playerAlive)) entries.push({ type: 'system', text: `The ${enemyBeforeTurn} was defeated.`, turn: turn.current });
      if (!next.playerAlive) entries.push({ type: 'danger', text: `${next.playerName} was defeated by the ${enemyBeforeTurn}.`, turn: turn.current });
      addEntries(entries); turn.current += 1; lastEnemy.current = enemyBeforeTurn; setGameState(next);
    } catch (requestError) { setError(requestError.message); } finally { setLoading(false); }
  }

  async function handleReward(reward) {
    setLoading(true); setError('');
    try {
      const previousHealth = gameState.playerHealth;
      const next = await selectReward(gameState.gameId, reward);
      const text = reward === 'RESTORE_HEALTH' ? `${next.playerName} restored ${next.playerHealth - previousHealth} health.` : `${next.playerName} gained one health potion.`;
      addEntries([{ type: 'healing', text, turn: '—' }, { type: 'system', text: `A ${next.enemyName} approaches.`, turn: '—' }]);
      lastEnemy.current = next.enemyName; setGameState(next);
    } catch (requestError) { setError(requestError.message); } finally { setLoading(false); }
  }

  function restart() {
    window.sessionStorage.removeItem(STORAGE_KEY); setGameState(null); setCombatLog([]); setError(''); setPlayerName(''); turn.current = 1;
  }

  if (restoring) return <div className="loading-screen"><div className="loader"/><p>Returning to the realm…</p></div>;
  return <div className="app-shell">
    {error && <div className="error-banner" role="alert"><span>{error}</span><button aria-label="Dismiss error" onClick={() => setError('')}>×</button></div>}
    {!gameState ? <CharacterSelection playerName={playerName} selectedClass={selectedClass} loading={loading} onNameChange={setPlayerName} onClassChange={setSelectedClass} onStart={startGame} /> : <main className="game-screen">
      <header className="game-header"><button className="wordmark" onClick={restart} aria-label="Abandon game and return to start">Realm <span>of</span> Java</button><p>Game <code>{gameState.gameId.slice(0, 8)}</code></p></header>
      <div className="game-layout"><div className="game-main"><BattleArena gameState={gameState} loading={loading} onAction={handleAction} />{gameState.awaitingReward && <RewardPanel playerName={gameState.playerName} loading={loading} onReward={handleReward} />}{gameState.gameCompleted && <GameOverPanel gameState={gameState} lastEnemy={lastEnemy.current} onRestart={restart} />}</div><CombatLog entries={combatLog} /></div>
      {loading && <div className="request-indicator" role="status"><span/>Resolving turn…</div>}
    </main>}
  </div>;
}
