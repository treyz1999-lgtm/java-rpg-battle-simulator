package com.ltd.rpg.dto;

import com.ltd.rpg.combat.RewardType;

/**
 * Request body used to select a reward.
 *
 * @param reward reward selected by the player
 */
public record RewardRequest(
        RewardType reward
) {
}