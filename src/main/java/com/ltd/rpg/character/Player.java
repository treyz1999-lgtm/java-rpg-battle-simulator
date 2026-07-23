package com.ltd.rpg.character;

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

    public abstract int useSpecialAbility(Enemy enemy);

    public int usePotion() {
        if (potionCount <= 0) {
            return 0;
        }

        int amountHealed = heal(POTION_HEAL_AMOUNT);

        if (amountHealed > 0) {
            potionCount--;
        }

        return amountHealed;
    }

    public int getPotionCount() {
        return potionCount;
    }
}