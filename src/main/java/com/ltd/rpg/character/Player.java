package com.ltd.rpg.character;

import com.ltd.rpg.combat.ActionResult;

public abstract class Player extends Combatant {

    private static final int POTION_HEAL_AMOUNT = 30;

    private int potionCount;

    public Player(
            String name,
            int maxHealth,
            int attackPower,
            int defensePower
    ) {
        super(name, maxHealth, attackPower, defensePower);
        this.potionCount = 3;
    }

    public abstract ActionResult useSpecialAbility(Enemy enemy);

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

    public void addPotion() {
        potionCount++;
    }
}