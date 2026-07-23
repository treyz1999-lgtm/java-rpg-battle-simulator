const configuredBaseUrl = import.meta.env.VITE_API_BASE_URL?.trim() ?? '';
const API_BASE_URL = configuredBaseUrl.replace(/\/$/, '');
const GAMES_PATH = `${API_BASE_URL}/api/games`;

async function request(path, options = {}) {
  let response;
  try {
    response = await fetch(path, {
      ...options,
      headers: { 'Content-Type': 'application/json', ...options.headers },
    });
  } catch {
    throw new Error('The game server is unavailable. Make sure Spring Boot is running on port 8080.');
  }

  const contentType = response.headers.get('content-type') ?? '';
  const body = contentType.includes('application/json')
    ? await response.json().catch(() => null)
    : await response.text().catch(() => '');

  if (!response.ok) {
    const message = body?.message || body?.error || (typeof body === 'string' && body)
      || `The server returned an unexpected ${response.status} response.`;
    throw new Error(message);
  }
  if (!body || typeof body !== 'object') throw new Error('The server returned an unexpected response.');
  return body;
}

export function createGame(gameRequest) {
  return request(GAMES_PATH, { method: 'POST', body: JSON.stringify(gameRequest) });
}
export function getGame(gameId) {
  return request(`${GAMES_PATH}/${encodeURIComponent(gameId)}`);
}
export function performAction(gameId, action) {
  return request(`${GAMES_PATH}/${encodeURIComponent(gameId)}/actions`, { method: 'POST', body: JSON.stringify({ action }) });
}
export function selectReward(gameId, reward) {
  return request(`${GAMES_PATH}/${encodeURIComponent(gameId)}/rewards`, { method: 'POST', body: JSON.stringify({ reward }) });
}
