package com.ltd.rpg.character;

import com.ltd.rpg.combat.ActionResult;

import java.util.Random;

/**
 * Playable character class with balanced combat statistics.
 *
 * <p>The Rogue's Shadow Strike has a chance to deal critical
 * damage.</p>
 */
public class Rogue extends Player {

    private final Random random;

    public Rogue(String name) {
        super(name, 95, 17, 5);
        this.random = new Random();
    }

    /**
     * Uses Shadow Strike against the supplied enemy.
     *
     * <p>The attack randomly deals either normal or critical damage.</p>
     *
     * @param enemy target of the attack
     * @return result containing damage and critical-hit status
     */
    @Override
    public ActionResult useSpecialAbility(Enemy enemy) {
        boolean criticalHit = random.nextBoolean();

        int damageMultiplier = criticalHit ? 3 : 1;

        int damage = enemy.takeDamage(
                getAttackPower() * damageMultiplier
        );

        return new ActionResult(
                getName(),
                "Shadow Strike",
                enemy.getName(),
                damage,
                0,
                criticalHit,
                true
        );
    }
}