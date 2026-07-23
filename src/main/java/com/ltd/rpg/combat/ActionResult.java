package com.ltd.rpg.combat;

/**
 * Immutable data carrier describing the result of a combat action.
 *
 * @param actorName character performing the action
 * @param actionName name of the action
 * @param targetName target affected by the action
 * @param damageDealt actual damage applied
 * @param healingDone actual health restored
 * @param criticalHit whether the action was a critical hit
 * @param successful whether the action completed successfully
 */
public record ActionResult(
        String actorName,
        String actionName,
        String targetName,
        int damageDealt,
        int healingDone,
        boolean criticalHit,
        boolean successful
) {

}