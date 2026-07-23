package com.ltd.rpg.service;

import com.ltd.rpg.character.Enemy;
import com.ltd.rpg.character.Mage;
import com.ltd.rpg.character.Player;
import com.ltd.rpg.character.Rogue;
import com.ltd.rpg.character.Warrior;
import com.ltd.rpg.combat.ActionResult;
import com.ltd.rpg.combat.ActionType;
import com.ltd.rpg.combat.BattleService;
import com.ltd.rpg.combat.RewardType;
import com.ltd.rpg.dto.GameStateResponse;
import com.ltd.rpg.dto.TurnResponse;
import com.ltd.rpg.model.GameSession;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages active games and coordinates game progression.
 */
@Service
public class GameService {

    private final BattleService battleService;
    private final Map<UUID, GameSession> sessions;

    public GameService(BattleService battleService) {
        this.battleService = battleService;
        this.sessions = new ConcurrentHashMap<>();
    }

    private Player createPlayer(
            String playerName,
            String characterClass
    ) {
        return switch (characterClass.trim().toUpperCase()) {
            case "WARRIOR" -> new Warrior(playerName);
            case "MAGE" -> new Mage(playerName);
            case "ROGUE" -> new Rogue(playerName);

            default -> throw new IllegalArgumentException(
                    "Unknown character class: " + characterClass
            );
        };
    }
    private Enemy createEnemy(int stage) {
        return switch (stage) {
            case 0 -> new Enemy(
                    "Goblin",
                    45,
                    10,
                    2
            );

            case 1 -> new Enemy(
                    "Skeleton",
                    65,
                    13,
                    4
            );

            case 2 -> new Enemy(
                    "Dragon",
                    120,
                    20,
                    7
            );

            default -> throw new IllegalArgumentException(
                    "No enemy exists for stage: " + stage
            );
        };
    }
    public GameStateResponse createGame(
            String playerName,
            String characterClass
    ) {
        Player player = createPlayer(
                playerName,
                characterClass
        );

        UUID gameId = UUID.randomUUID();

        GameSession session = new GameSession(
                gameId,
                player,
                createEnemy(0)
        );

        sessions.put(gameId, session);

        return toResponse(session);
    }

    public GameStateResponse getGame(UUID gameId) {
        return toResponse(findSession(gameId));
    }

    private GameSession findSession(UUID gameId) {
        GameSession session = sessions.get(gameId);

        if (session == null) {
            throw new IllegalArgumentException(
                    "Game session not found: " + gameId
            );
        }

        return session;
    }

    private GameStateResponse toResponse(
            GameSession session
    ) {
        Player player = session.getPlayer();
        Enemy enemy = session.getCurrentEnemy();

        return new GameStateResponse(
                session.getId(),
                player.getName(),
                player.getClass().getSimpleName(),
                player.getHealth(),
                player.getMaxHealth(),
                player.getAttackPower(),
                player.getDefensePower(),
                player.getPotionCount(),
                enemy == null ? null : enemy.getName(),
                enemy == null ? 0 : enemy.getHealth(),
                enemy == null ? 0 : enemy.getMaxHealth(),
                session.getStage(),
                session.isAwaitingReward(),
                session.isCompleted(),
                player.isAlive()
        );
    }
    public TurnResponse processAction(
            UUID gameId,
            ActionType action
    ) {
        GameSession session = findSession(gameId);

        validateCombatAvailable(session);

        Player player = session.getPlayer();
        Enemy enemy = session.getCurrentEnemy();

        ActionResult playerResult =
                battleService.processPlayerAction(
                        player,
                        enemy,
                        action
                );

        ActionResult enemyResult = null;

        if (enemy.isAlive()) {
            enemyResult = battleService.processEnemyTurn(
                    enemy,
                    player
            );
        }

        updateAfterBattle(session);

        return new TurnResponse(
                playerResult,
                enemyResult,
                toResponse(session)
        );
    }

    private void validateCombatAvailable(
            GameSession session
    ) {
        if (session.isCompleted()) {
            throw new IllegalStateException(
                    "This game has already ended."
            );
        }

        if (session.isAwaitingReward()) {
            throw new IllegalStateException(
                    "Select a reward before continuing."
            );
        }

        if (!session.getPlayer().isAlive()) {
            throw new IllegalStateException(
                    "The player has been defeated."
            );
        }
    }

    private void updateAfterBattle(GameSession session) {
        Player player = session.getPlayer();
        Enemy enemy = session.getCurrentEnemy();

        if (!player.isAlive()) {
            session.complete();
            return;
        }

        if (enemy.isAlive()) {
            return;
        }

        if (session.getStage() >= 2) {
            session.setCurrentEnemy(null);
            session.complete();
            return;
        }

        session.setAwaitingReward(true);
    }
    public GameStateResponse selectReward(
            UUID gameId,
            RewardType reward
    ) {
        GameSession session = findSession(gameId);

        if (!session.isAwaitingReward()) {
            throw new IllegalStateException(
                    "This game is not awaiting a reward."
            );
        }

        Player player = session.getPlayer();

        switch (reward) {
            case RESTORE_HEALTH -> player.heal(40);
            case ADD_POTION -> player.addPotion();
        }

        session.advanceStage();
        session.setCurrentEnemy(
                createEnemy(session.getStage())
        );
        session.setAwaitingReward(false);

        return toResponse(session);
    }

}