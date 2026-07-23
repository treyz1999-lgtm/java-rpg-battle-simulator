package com.ltd.rpg.character;

import java.util.Random;

public class Rogue extends Player {

    private final Random random;

    public Rogue(String name) {
        super(name, 95, 17, 5);
        this.random = new Random();
    }
    @Override
    public int useSpecialAbility(Enemy enemy) {
        boolean criticalHit = random.nextBoolean();

        int damageMultiplier = criticalHit ? 3 : 1;

        return enemy.takeDamage(
                getAttackPower() * damageMultiplier
        );
    }

}

