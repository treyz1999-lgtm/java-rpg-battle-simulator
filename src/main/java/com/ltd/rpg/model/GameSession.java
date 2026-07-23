package com.ltd.rpg.model;

import com.ltd.rpg.character.Enemy;
import com.ltd.rpg.character.Player;

import java.util.UUID;

/**
 * Stores the mutable state of one active game.
 */
public class GameSession {

    private final UUID id;
    private final Player player;

    private Enemy currentEnemy;
    private int stage;
    private boolean awaitingReward;
    private boolean completed;

    public GameSession(
            UUID id,
            Player player,
            Enemy currentEnemy
    ) {
        this.id = id;
        this.player = player;
        this.currentEnemy = currentEnemy;
        this.stage = 0;
        this.awaitingReward = false;
        this.completed = false;
    }

    public UUID getId() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public Enemy getCurrentEnemy() {
        return currentEnemy;
    }

    public void setCurrentEnemy(Enemy currentEnemy) {
        this.currentEnemy = currentEnemy;
    }

    public int getStage() {
        return stage;
    }

    public void advanceStage() {
        stage++;
    }

    public boolean isAwaitingReward() {
        return awaitingReward;
    }

    public void setAwaitingReward(boolean awaitingReward) {
        this.awaitingReward = awaitingReward;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void complete() {
        completed = true;
    }
}