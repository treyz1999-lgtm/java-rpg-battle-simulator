package com.ltd.rpg;

import com.ltd.rpg.character.*;
import com.ltd.rpg.ui.ConsoleUI;

import com.ltd.rpg.character.Enemy;
import com.ltd.rpg.character.Mage;
import com.ltd.rpg.character.Player;
import com.ltd.rpg.character.Rogue;
import com.ltd.rpg.character.Warrior;


public class Game {

    private final ConsoleUI ui;
    private Player player;

    public Game() {
        this.ui = new ConsoleUI();
    }

    public void start() {
        ui.displayMessage("================================");
        ui.displayMessage("          RPG GAME");
        ui.displayMessage("================================");

        String playerName = ui.askForName();
        int selection = ui.chooseCharacter();

        player = createPlayer(selection, playerName);

        ui.displayMessage("");
        ui.displayMessage(
                "You selected "
                        + player.getClass().getSimpleName()
                        + "."
        );

        Enemy goblin = new Enemy(
                "Goblin",
                45,
                10,
                2
        );

        runBattle(goblin);

        ui.close();
    }

    private Player createPlayer(int selection, String name) {
        return switch (selection) {
            case 1 -> new Warrior(name);
            case 2 -> new Mage(name);
            case 3 -> new Rogue(name);
            default -> throw new IllegalArgumentException(
                    "Unknown character selection: " + selection
            );
        };
    }

    private void runBattle(Enemy enemy) {
        ui.displayMessage("");
        ui.displayMessage("A " + enemy.getName() + " appears!");

        while (player.isAlive() && enemy.isAlive()) {
            displayBattleStatus(enemy);

            int action = ui.chooseBattleAction();
            processPlayerAction(action, enemy);

            if (enemy.isAlive()) {
                processEnemyTurn(enemy);
            }
        }

        if (player.isAlive()) {
            ui.displayMessage("");
            ui.displayMessage(
                    "You defeated the " + enemy.getName() + "!"
            );
        }
        else {
            ui.displayMessage("");
            ui.displayMessage("You were defeated.");
        }
    }

    private void processPlayerAction(int action, Enemy enemy) {
        switch (action) {
            case 1 -> {
                int damage = player.attack(enemy);

                ui.displayMessage(
                        player.getName()
                                + " dealt "
                                + damage
                                + " damage."
                );
            }

            case 2 -> {
                int damage = player.useSpecialAbility(enemy);

                ui.displayMessage(
                        player.getName()
                                + " used a special ability for "
                                + damage
                                + " damage."
                );
            }

            case 3 -> {
                int amountHealed = player.usePotion();

                if (amountHealed > 0) {
                    ui.displayMessage(
                            player.getName()
                                    + " restored "
                                    + amountHealed
                                    + " health."
                    );
                }
                else {
                    ui.displayMessage(
                            "You could not use a potion."
                    );
                }
            }

            default -> throw new IllegalArgumentException(
                    "Unknown battle action: " + action
            );
        }
    }

    private void processEnemyTurn(Enemy enemy) {
        int damage = enemy.attack(player);

        ui.displayMessage(
                enemy.getName()
                        + " dealt "
                        + damage
                        + " damage."
        );
    }

    private void displayBattleStatus(Enemy enemy) {
        ui.displayMessage("");
        ui.displayMessage("--------------------------------");
        ui.displayMessage(
                player.getName()
                        + ": "
                        + player.getHealth()
                        + "/"
                        + player.getMaxHealth()
                        + " HP"
        );

        ui.displayMessage(
                enemy.getName()
                        + ": "
                        + enemy.getHealth()
                        + "/"
                        + enemy.getMaxHealth()
                        + " HP"
        );

        ui.displayMessage(
                "Potions: " + player.getPotionCount()
        );
        ui.displayMessage("--------------------------------");
    }
}