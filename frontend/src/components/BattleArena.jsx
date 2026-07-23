import FantasyIcon from './FantasyIcon';

function HealthBar({ current, maximum, label }) {
  const percentage = maximum > 0 ? Math.max(0, Math.min(100, (current / maximum) * 100)) : 0;
  return <div className="health-wrap"><div className="health-label"><span>{label}</span><strong>{current} / {maximum}</strong></div><div className="health-track" role="progressbar" aria-label={`${label}: ${current} of ${maximum}`} aria-valuemin="0" aria-valuemax={maximum} aria-valuenow={current}><span className={percentage <= 25 ? 'critical-health' : ''} style={{ width: `${percentage}%` }} /></div></div>;
}

function CombatantCard({ kind, name, subtitle, health, maxHealth, attack, defense, potions }) {
  return <article className={`combatant-card ${kind}`}>
    <div className="portrait"><FantasyIcon type={kind === 'enemy' ? 'enemy' : subtitle} size={62} /></div>
    <div className="combatant-heading"><div><p>{kind === 'enemy' ? 'Opponent' : 'Adventurer'}</p><h2>{name}</h2></div><span className="class-badge">{subtitle}</span></div>
    <HealthBar current={health} maximum={maxHealth} label="Health" />
    {kind === 'player' && <dl className="stat-grid"><div><dt>Attack</dt><dd>{attack}</dd></div><div><dt>Defense</dt><dd>{defense}</dd></div><div><dt>Potions</dt><dd>{potions}</dd></div></dl>}
  </article>;
}

export default function BattleArena({ gameState, loading, onAction }) {
  const locked = loading || gameState.awaitingReward || gameState.gameCompleted || !gameState.playerAlive || !gameState.enemyName;
  const encounter = Math.min(gameState.stage + 1, 3);
  const special = gameState.playerClass === 'Warrior' ? 'Power Strike' : gameState.playerClass === 'Mage' ? 'Fireball' : 'Shadow Strike';
  return <section className="battle-section" aria-label="Battle arena">
    <div className="encounter-line"><span>Encounter {encounter} of 3</span><div><i className="active"/><i className={gameState.stage >= 1 ? 'active' : ''}/><i className={gameState.stage >= 2 ? 'active' : ''}/></div></div>
    <div className="arena-grid">
      <CombatantCard kind="player" name={gameState.playerName} subtitle={gameState.playerClass} health={gameState.playerHealth} maxHealth={gameState.playerMaxHealth} attack={gameState.playerAttack} defense={gameState.playerDefense} potions={gameState.potionCount} />
      <div className="versus" aria-hidden="true">VS</div>
      <CombatantCard kind="enemy" name={gameState.enemyName || 'Defeated'} subtitle={`Stage ${encounter}`} health={gameState.enemyHealth} maxHealth={gameState.enemyMaxHealth} />
    </div>
    <div className="action-panel" aria-label="Combat actions">
      <button disabled={locked} onClick={() => onAction('BASIC_ATTACK')}><span>⚔</span><strong>Basic Attack</strong><small>Reliable weapon strike</small></button>
      <button disabled={locked} onClick={() => onAction('SPECIAL_ABILITY')}><span>✦</span><strong>Special Ability</strong><small>{special}</small></button>
      <button disabled={locked} onClick={() => onAction('USE_POTION')}><span>◆</span><strong>Use Potion</strong><small>{gameState.potionCount} remaining</small></button>
    </div>
  </section>;
}
