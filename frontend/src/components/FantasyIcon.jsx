export default function FantasyIcon({ type, size = 48 }) {
  const common = { width: size, height: size, viewBox: '0 0 64 64', fill: 'none', 'aria-hidden': true };
  if (type === 'Mage') return <svg {...common}><path d="M32 6 18 29h9l-7 29 25-34H35L32 6Z" stroke="currentColor" strokeWidth="3" strokeLinejoin="round"/><circle cx="44" cy="14" r="4" fill="currentColor"/></svg>;
  if (type === 'Rogue') return <svg {...common}><path d="m12 50 32-32 7 7-32 32-9 2 2-9Z" stroke="currentColor" strokeWidth="3"/><path d="m39 14 11-8 8 8-8 11M16 44l5 5" stroke="currentColor" strokeWidth="3" strokeLinecap="round"/></svg>;
  if (type === 'enemy') return <svg {...common}><path d="M13 29c0-14 8-22 19-22s19 8 19 22v22l-8-5-5 7-6-7-6 7-5-7-8 5V29Z" stroke="currentColor" strokeWidth="3"/><path d="M21 28h8M35 28h8M27 38l5 3 5-3" stroke="currentColor" strokeWidth="3" strokeLinecap="round"/></svg>;
  return <svg {...common}><path d="M32 6 51 14v15c0 13-8 23-19 29-11-6-19-16-19-29V14L32 6Z" stroke="currentColor" strokeWidth="3"/><path d="M32 17v29M22 27h20" stroke="currentColor" strokeWidth="3" strokeLinecap="round"/></svg>;
}
