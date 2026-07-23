package com.ltd.rpg.character;

/**
 * Concrete combatant representing an enemy character.
 *
 * <p>Enemies currently use the shared combat behavior inherited
 * from Combatant and are configured using different names and
 * statistics.</p>
 */
public class Enemy extends Combatant {

    /**
     * Creates an enemy with the supplied combat statistics.
     *
     * @param name enemy name
     * @param maxHealth maximum and initial health
     * @param attackPower base attack strength
     * @param defensePower damage reduction value
     */
    public Enemy(
            String name,
            int maxHealth,
            int attackPower,
            int defensePower
    ) {
        super(
                name,
                maxHealth,
                attackPower,
                defensePower
        );
    }
}