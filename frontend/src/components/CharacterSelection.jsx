import FantasyIcon from './FantasyIcon';

const CLASSES = [
  { name: 'Warrior', value: 'WARRIOR', special: 'Power Strike', traits: 'High health · High defense' },
  { name: 'Mage', value: 'MAGE', special: 'Fireball', traits: 'High attack · Lower resilience' },
  { name: 'Rogue', value: 'ROGUE', special: 'Shadow Strike', traits: 'Balanced · Critical-hit chance' },
];

export default function CharacterSelection({ playerName, selectedClass, loading, onNameChange, onClassChange, onStart }) {
  return <main className="start-screen">
    <div className="brand-mark" aria-hidden="true">RJ</div>
    <p className="eyebrow">A turn-based adventure</p>
    <h1>Realm of Java</h1>
    <p className="lead">Choose your path. Survive three encounters. Defeat the Dragon.</p>
    <form className="start-form" onSubmit={onStart}>
      <label className="name-field"><span>Character name</span><input value={playerName} onChange={(event) => onNameChange(event.target.value)} maxLength="30" autoComplete="off" placeholder="Enter a name" required /></label>
      <fieldset><legend>Choose your class</legend><div className="class-grid">
        {CLASSES.map((character) => {
          const selected = selectedClass === character.value;
          return <button className={`class-card ${selected ? 'selected' : ''}`} type="button" key={character.value} aria-pressed={selected} onClick={() => onClassChange(character.value)}>
            <span className="class-icon"><FantasyIcon type={character.name} /></span><strong>{character.name}</strong><span>{character.traits}</span><small>{character.special}</small>
          </button>;
        })}
      </div></fieldset>
      <button className="primary-button start-button" disabled={loading || !playerName.trim() || !selectedClass}>{loading ? 'Opening the realm…' : 'Start game'}</button>
    </form>
  </main>;
}
