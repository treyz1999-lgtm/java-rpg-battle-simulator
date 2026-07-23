# Realm of Java

Realm of Java is a short turn-based RPG with a Spring Boot REST API, an existing Java CLI/domain layer, and a responsive React frontend. Choose a Warrior, Mage, or Rogue and fight the Goblin, Skeleton, and Dragon without duplicating combat rules in the browser.

## V2 features

- React and Vite browser interface
- Character creation and class selection
- API-driven player and enemy statistics
- Basic attacks, class abilities, and health potions
- Structured combat log based on backend `ActionResult` records
- Reward selection between encounters
- Victory, defeat, and replay flows
- Session refresh recovery using the game ID
- Responsive, keyboard-accessible layout with reduced-motion support

The Java backend remains the source of truth for damage, healing, critical hits, enemy progression, rewards, and completion state. The frontend sends commands and renders API responses only.

## Requirements

- JDK 21 or newer
- Maven 3.9+ (or IntelliJ IDEA bundled Maven)
- Node.js 20+ and npm

## Run the backend

From the repository root:

```powershell
mvn spring-boot:run
```

You can also run `com.ltd.rpg.Main` from IntelliJ IDEA. The backend uses `http://localhost:8080` by default.

## Run the frontend

In a second terminal:

```powershell
cd frontend
npm install
npm run dev
```

Open `http://localhost:5173`. Vite proxies relative `/api` requests to port 8080.

To make direct browser requests instead, copy `frontend/.env.example` to `frontend/.env` and set:

```dotenv
VITE_API_BASE_URL=http://localhost:8080
```

The backend permits the local Vite origin `http://localhost:5173` for `GET` and `POST` requests under `/api/**`.

## Architecture

```text
React components
      |
frontend/src/api/gameApi.js
      |
Spring Boot /api/games REST controller
      |
GameService -> BattleService -> Java domain classes
```

The verified endpoint and JSON contract is documented in [API_CONTRACT.md](API_CONTRACT.md).

## Project layout

```text
src/main/java/       Spring Boot API, CLI, and game domain
frontend/src/        React application and API client
frontend/vite.config.js
API_CONTRACT.md       Source-derived REST contract
```

## Development ports

- Spring Boot API: `8080`
- Vite frontend: `5173`

## Current limitations

- Game sessions live only in backend memory and disappear when the backend restarts.
- A browser refresh restores the active game state during the same tab session, but earlier combat-log text cannot be restored because the API does not expose history.
- There is no database, authentication, or multiplayer support.
- The CLI and REST progression coexist; the React frontend communicates only with the REST API.
