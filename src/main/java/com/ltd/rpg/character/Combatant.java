package com.ltd.rpg.character;

public abstract class Combatant {

    private String name;
    private int health;
    private int maxHealth;
    private int attackPower;
    private int defensePower;

    public Combatant(
            String name,
            int maxHealth,
            int attackPower,
            int defensePower
    ) {
        this.name = name;
        this.health = maxHealth;
        this.maxHealth = maxHealth;
        this.attackPower = attackPower;
        this.defensePower = defensePower;
    }

    public int attack(Combatant target) {
        return target.takeDamage(attackPower);
    }

    public int takeDamage(int incomingDamage) {
        int actualDamage = Math.max(
                1,
                incomingDamage - defensePower
        );

        health = Math.max(
                0,
                health - actualDamage
        );

        return actualDamage;
    }

    public int heal(int amount) {
        if (amount <= 0 || !isAlive()) {
            return 0;
        }

        int previousHealth = health;

        health = Math.min(
                maxHealth,
                health + amount
        );

        return health - previousHealth;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getDefensePower() {
        return defensePower;
    }
}