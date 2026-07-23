package com.ltd.rpg.character;

import com.ltd.rpg.combat.ActionResult;

import java.util.Random;

public class Rogue extends Player {

    private final Random random;

    public Rogue(String name) {
        super(name, 95, 17, 5);
        this.random = new Random();
    }

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