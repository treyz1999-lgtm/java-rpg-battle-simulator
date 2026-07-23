package com.ltd.rpg.dto;

import com.ltd.rpg.combat.ActionType;

/**
 * Request body used to perform a combat action.
 *
 * @param action action selected by the player
 */
public record PlayerActionRequest(
        ActionType action
) {
}