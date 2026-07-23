package com.ltd.rpg.character;

import com.ltd.rpg.combat.ActionResult;

public abstract class Player extends Combatant {

    private static final int POTION_HEAL_AMOUNT = 30;

    private int potionCount;

    /**
     * Abstract base class for all playable character classes.
     *
     * <p>Player extends Combatant with player-specific behavior,
     * including potion management and a required special ability.</p>
     *
     * <p>Concrete subclasses must implement their own version of
     * useSpecialAbility.</p>
     */
    public Player(
            String name,
            int maxHealth,
            int attackPower,
            int defensePower
    ) {
        super(name, maxHealth, attackPower, defensePower);
        this.potionCount = 3;
    }

    /**
     * Performs the class-specific special ability.
     *
     * @param enemy enemy targeted by the ability
     * @return structured result describing the action
     */
    public abstract ActionResult useSpecialAbility(Enemy enemy);

    /**
     * Uses one health potion when available and when healing is possible.
     *
     * <p>A potion is only consumed when it successfully restores health.</p>
     *
     * @return result describing the healing action or its failure
     */
    public ActionResult usePotion() {
        if (potionCount <= 0) {
            return new ActionResult(
                    getName(),
                    "Health Potion",
                    getName(),
                    0,
                    0,
                    false,
                    false
            );
        }

        int amountHealed = heal(POTION_HEAL_AMOUNT);

        if (amountHealed <= 0) {
            return new ActionResult(
                    getName(),
                    "Health Potion",
                    getName(),
                    0,
                    0,
                    false,
                    false
            );
        }

        potionCount--;

        return new ActionResult(
                getName(),
                "Health Potion",
                getName(),
                0,
                amountHealed,
                false,
                true
        );
    }

    public int getPotionCount() {
        return potionCount;
    }

    /**
     * Adds one potion to the player's inventory.
     */
    public void addPotion() {
        potionCount++;
    }
}