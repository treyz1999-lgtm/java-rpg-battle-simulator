package com.ltd.rpg.character;

public class Enemy extends Combatant {

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