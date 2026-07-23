package com.ltd.rpg.character;

import com.ltd.rpg.combat.ActionResult;

public class Mage extends Player {

    public Mage(String name) {
        super(name, 80, 22, 3);
    }

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