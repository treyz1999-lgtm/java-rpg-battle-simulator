import { useEffect, useRef } from 'react';

export function CombatLog({ entries }) {
  const logRef = useRef(null);
  useEffect(() => { logRef.current?.scrollTo({ top: logRef.current.scrollHeight, behavior: 'smooth' }); }, [entries]);
  return <aside className="combat-log" aria-label="Combat log"><div className="panel-title"><h2>Combat log</h2><span>Live</span></div><ol ref={logRef} aria-live="polite">
    {entries.map((entry) => <li className={entry.type} key={entry.id}><time>{entry.turn}</time><p>{entry.text}</p></li>)}
  </ol></aside>;
}

export function RewardPanel({ playerName, loading, onReward }) {
  return <section className="overlay-panel reward-panel" aria-labelledby="reward-title"><p className="eyebrow">Battle won</p><h2 id="reward-title">Choose your reward</h2><p>{playerName}, prepare for the next encounter.</p><div className="reward-grid">
    <button disabled={loading} onClick={() => onReward('RESTORE_HEALTH')}><span className="reward-symbol healing">+</span><strong>Restore health</strong><small>Recover up to 40 health</small></button>
    <button disabled={loading} onClick={() => onReward('ADD_POTION')}><span className="reward-symbol potion">◆</span><strong>Gain one potion</strong><small>Add a potion to your inventory</small></button>
  </div></section>;
}

export function GameOverPanel({ gameState, lastEnemy, onRestart }) {
  const victory = gameState.playerAlive;
  return <section className={`overlay-panel game-over ${victory ? 'victory' : 'defeat'}`} aria-labelledby="game-over-title"><div className="result-symbol" aria-hidden="true">{victory ? '♛' : '×'}</div><p className="eyebrow">{victory ? 'The realm is safe' : 'The journey ends'}</p><h2 id="game-over-title">{victory ? 'Dragon defeated' : 'You were defeated'}</h2><p>{victory ? `${gameState.playerName} survived with ${gameState.playerHealth} health remaining.` : `${lastEnemy || 'Your enemy'} proved victorious this time.`}</p><button className="primary-button" onClick={onRestart}>{victory ? 'Play again' : 'Try again'}</button></section>;
}
