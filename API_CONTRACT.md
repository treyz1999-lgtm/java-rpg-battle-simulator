# Realm of Java REST API

This contract is derived from the Spring Boot controller, request records, response records, and enums in `src/main/java`.

## Base URL

- Development backend: `http://localhost:8080`
- API base path: `/api/games`

## Routes

### `POST /api/games`

Request:

```json
{ "playerName": "Lewis", "characterClass": "WARRIOR" }
```

`characterClass` accepts `WARRIOR`, `MAGE`, or `ROGUE`.

Returns `GameStateResponse`.

### `GET /api/games/{gameId}`

Returns the current `GameStateResponse` for an in-memory game session.

### `POST /api/games/{gameId}/actions`

Request:

```json
{ "action": "BASIC_ATTACK" }
```

`action` accepts `BASIC_ATTACK`, `SPECIAL_ABILITY`, or `USE_POTION`.

Returns a `TurnResponse` containing `playerAction`, nullable `enemyAction`, and `gameState`.

### `POST /api/games/{gameId}/rewards`

Request:

```json
{ "reward": "RESTORE_HEALTH" }
```

`reward` accepts `RESTORE_HEALTH` or `ADD_POTION`. Returns the updated `GameStateResponse`.

## Response shapes

`GameStateResponse` fields: `gameId`, `playerName`, `playerClass`, `playerHealth`, `playerMaxHealth`, `playerAttack`, `playerDefense`, `potionCount`, `enemyName`, `enemyHealth`, `enemyMaxHealth`, `stage`, `awaitingReward`, `gameCompleted`, and `playerAlive`.

`ActionResult` fields: `actorName`, `actionName`, `targetName`, `damageDealt`, `healingDone`, `criticalHit`, and `successful`.

Games are stored in backend memory only and are lost whenever the Spring Boot process restarts.


## Errors

Expected API errors use JSON fields: status, error, message, and timestamp. Missing games return HTTP 404, invalid arguments return HTTP 400, and invalid game-state operations return HTTP 409.
