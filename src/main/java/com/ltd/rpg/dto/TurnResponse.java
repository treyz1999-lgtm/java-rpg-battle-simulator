package com.ltd.rpg.dto;

import com.ltd.rpg.combat.ActionResult;

/**
 * Response returned after processing one combat turn.
 */
public record TurnResponse(
        ActionResult playerAction,
        ActionResult enemyAction,
        GameStateResponse gameState
) {
}