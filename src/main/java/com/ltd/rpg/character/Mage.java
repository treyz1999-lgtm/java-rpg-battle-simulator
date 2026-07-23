package com.ltd.rpg.character;

public class Mage extends Player {

    public Mage(String name) {
        super(name, 80, 22, 3);
    }

    @Override
    public int useSpecialAbility(Enemy enemy) {
        return enemy.takeDamage(getAttackPower() + 15);
    }
}