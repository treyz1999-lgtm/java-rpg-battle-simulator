package com.ltd.rpg.dto;

/**
 * Request body used to create a new game session.
 *
 * @param playerName name assigned to the player
 * @param characterClass requested playable character class
 */
public record CreateGameRequest(
        String playerName,
        String characterClass
) {
}