package com.ltd.rpg.dto;

import java.util.UUID;

/**
 * Public API representation of the current game state.
 */
public record GameStateResponse(
        UUID gameId,
        String playerName,
        String playerClass,
        int playerHealth,
        int playerMaxHealth,
        int playerAttack,
        int playerDefense,
        int potionCount,
        String enemyName,
        int enemyHealth,
        int enemyMaxHealth,
        int stage,
        boolean awaitingReward,
        boolean gameCompleted,
        boolean playerAlive
) {
}