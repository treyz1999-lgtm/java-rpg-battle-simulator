package com.ltd.rpg.character;

import com.ltd.rpg.combat.ActionResult;

/**
 * Abstract base class for any character capable of participating
 * in combat.
 *
 * <p>Combatant stores shared attributes including name, health,
 * attack power, and defense power. It also provides common behavior
 * for attacking, receiving damage, healing, and checking whether
 * the character is alive.</p>
 *
 * <p>This class cannot be instantiated directly. Concrete subclasses
 * include Player and Enemy.</p>
 */
public abstract class Combatant {

    private String name;
    private int health;
    private int maxHealth;
    private int attackPower;
    private int defensePower;

    /**
     * Creates a combatant with the supplied base statistics.
     *
     * @param name display name of the combatant
     * @param maxHealth maximum and initial health
     * @param attackPower base attack strength
     * @param defensePower amount subtracted from incoming damage
     */
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

    /**
     * Performs a basic attack against another combatant.
     *
     * @param target combatant receiving the attack
     * @return actual damage dealt after defense is applied
     */
    public int attack(Combatant target) {
        return target.takeDamage(attackPower);
    }

    /**
     * Performs a basic attack and returns a structured description
     * of the result.
     *
     * @param target combatant receiving the attack
     * @return immutable result describing the action
     */
    public ActionResult performBasicAttack(Combatant target) {
        int damage = attack(target);

        return new ActionResult(
                getName(),
                "Basic Attack",
                target.getName(),
                damage,
                0,
                false,
                true
        );
    }

    /**
     * Applies incoming damage after subtracting defense.
     *
     * <p>Every successful attack deals at least one damage, and health
     * cannot fall below zero.</p>
     *
     * @param incomingDamage damage before defense is applied
     * @return actual health removed
     */
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

    /**
     * Restores health without exceeding maximum health.
     *
     * @param amount requested healing amount
     * @return actual health restored
     */
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

    /**
     * Determines whether the combatant has remaining health.
     *
     * @return true when health is greater than zero
     */
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