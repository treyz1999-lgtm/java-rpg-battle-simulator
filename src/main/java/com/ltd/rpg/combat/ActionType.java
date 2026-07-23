package com.ltd.rpg.combat;

/**
 * Defines the actions available to the player during combat.
 */
public enum ActionType {

    /**
     * Standard attack using the player's base attack power.
     */
    BASIC_ATTACK,

    /**
     * Class-specific special ability.
     */
    SPECIAL_ABILITY,

    /**
     * Attempt to restore health using a potion.
     */
    USE_POTION
}