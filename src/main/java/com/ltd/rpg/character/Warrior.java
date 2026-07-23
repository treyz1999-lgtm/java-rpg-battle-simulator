package com.ltd.rpg.character;

public class Warrior extends Player {

    public Warrior(String name) {
        super(name, 120, 15, 8);
    }

    @Override
    public int useSpecialAbility(Enemy enemy) {
        return enemy.takeDamage(getAttackPower() * 2);
    }
}