package com.ltd.rpg.character;

import com.ltd.rpg.combat.ActionResult;

/**
 * Playable character class with high health and defense.
 *
 * <p>The Warrior's special ability is Power Strike, which deals
 * double the Warrior's base attack power before enemy defense
 * is applied.</p>
 */
public class Warrior extends Player {

    public Warrior(String name) {
        super(name, 120, 15, 8);
    }

    /**
     * Uses Power Strike against the supplied enemy.
     *
     * @param enemy target of the attack
     * @return result containing the damage dealt
     */
    @Override
    public ActionResult useSpecialAbility(Enemy enemy) {
        int damage = enemy.takeDamage(
                getAttackPower() * 2
        );

        return new ActionResult(
                getName(),
                "Power Strike",
                enemy.getName(),
                damage,
                0,
                false,
                true
        );
    }
}