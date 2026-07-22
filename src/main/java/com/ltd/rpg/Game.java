package com.ltd.rpg;

import com.ltd.rpg.character.Combatant;
import com.ltd.rpg.character.Enemy;


public class Game {

    public void start() {
        Enemy warrior = new Enemy(
                "Temporary Warrior",
                100,
                15,
                5
        );

        Enemy goblin = new Enemy(
                "Goblin",
                40,
                8,
                2
        );

        System.out.println(
                goblin.getName()
                        + " HP: "
                        + goblin.getHealth()
                        + "/"
                        + goblin.getMaxHealth()
        );

        int damageDealt = warrior.attack(goblin);

        System.out.println(
                warrior.getName()
                        + " dealt "
                        + damageDealt
                        + " damage."
        );

        System.out.println(
                goblin.getName()
                        + " HP: "
                        + goblin.getHealth()
                        + "/"
                        + goblin.getMaxHealth()
        );
    }
}
