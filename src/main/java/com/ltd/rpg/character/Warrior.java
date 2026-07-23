package com.ltd.rpg.character;

import com.ltd.rpg.combat.ActionResult;

public class Warrior extends Player {

    public Warrior(String name) {
        super(name, 120, 15, 8);
    }

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