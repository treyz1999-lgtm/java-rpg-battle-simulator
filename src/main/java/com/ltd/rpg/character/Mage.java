package com.ltd.rpg.character;

import com.ltd.rpg.combat.ActionResult;

/**
 * Playable character class with high offensive power and lower
 * health and defense.
 *
 * <p>The Mage's special ability is Fireball, which adds bonus
 * damage to the Mage's base attack power.</p>
 */
public class Mage extends Player {

    public Mage(String name) {
        super(name, 80, 22, 3);
    }

    /**
     * Casts Fireball on the supplied enemy.
     *
     * @param enemy target of the spell
     * @return result containing the damage dealt
     */
    @Override
    public ActionResult useSpecialAbility(Enemy enemy) {
        int damage = enemy.takeDamage(
                getAttackPower() + 15
        );

        return new ActionResult(
                getName(),
                "Fireball",
                enemy.getName(),
                damage,
                0,
                false,
                true
        );
    }
}