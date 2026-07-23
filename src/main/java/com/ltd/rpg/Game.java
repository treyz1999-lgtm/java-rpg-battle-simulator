package com.ltd.rpg;

import com.ltd.rpg.character.*;
import com.ltd.rpg.combat.ActionResult;
import com.ltd.rpg.combat.ActionType;
import com.ltd.rpg.combat.BattleService;
import com.ltd.rpg.ui.ConsoleUI;

import com.ltd.rpg.character.Enemy;
import com.ltd.rpg.character.Mage;
import com.ltd.rpg.character.Player;
import com.ltd.rpg.character.Rogue;
import com.ltd.rpg.character.Warrior;

/**
 * Controls the overall flow of the game.
 *
 * <p>The Game class creates the selected player, manages enemy
 * progression, starts battles, provides rewards, and determines
 * the final outcome.</p>
 *
 * <p>Individual combat rules are delegated to BattleService,
 * while input and output are delegated to ConsoleUI.</p>
 */
public class Game {

    private final ConsoleUI ui;
    private Player player;
    private final BattleService battleService;

    public Game() {
        this.ui = new ConsoleUI();
        this.battleService = new BattleService();
    }

    /**
     * Starts a new game session.
     *
     * <p>The player enters a name, selects a character class,
     * fights a sequence of enemies, and receives rewards between
     * successful battles.</p>
     */
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

        if (!player.isAlive()) {
            ui.close();
            return;
        }
        giveReward();

        Enemy skeleton = new Enemy(
                "Skeleton",
                65,
                13,
                4
        );

        runBattle(skeleton);

        if (!player.isAlive()) {
            ui.close();
            return;
        }
        giveReward();

        Enemy dragon = new Enemy(
                "Dragon",
                200,
                20,
                7
        );

        runBattle(dragon);

        if (player.isAlive()) {
            ui.displayMessage("");
            ui.displayMessage(
                    "You defeated the Dragon and completed the game!"
            );
        }

        ui.close();
    }

    /**
     * Creates the selected player subclass.
     *
     * @param selection numeric character selection from the UI
     * @param name name assigned to the player
     * @return a Warrior, Mage, or Rogue stored as a Player
     * @throws IllegalArgumentException if the selection is not recognized
     */
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

    /**
     * Runs a complete battle between the current player and an enemy.
     *
     * @param enemy enemy participating in the battle
     */
    private void runBattle(Enemy enemy) {
        ui.displayMessage("");
        ui.displayMessage("A " + enemy.getName() + " appears!");

        while (!battleService.isBattleOver(player, enemy)) {
            displayBattleStatus(enemy);

            ActionType action = ui.chooseBattleAction();

            ActionResult playerResult =
                    battleService.processPlayerAction(
                            player,
                            enemy,
                            action
                    );

            ui.displayActionResult(playerResult);

            if (enemy.isAlive()) {
                ActionResult enemyResult =
                        battleService.processEnemyTurn(
                                enemy,
                                player
                        );

                ui.displayActionResult(enemyResult);
            }
        }

        displayBattleOutcome(enemy);
    }

    private void displayBattleOutcome(Enemy enemy) {
        ui.displayMessage("");

        if (player.isAlive()) {
            ui.displayMessage(
                    "You defeated the " + enemy.getName() + "!"
            );
        }
        else {
            ui.displayMessage("You were defeated.");
        }
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

    /**
     * Gives the player a choice of reward after winning a battle.
     */
    private void giveReward() {
        int rewardChoice = ui.chooseReward();

        switch (rewardChoice) {
            case 1 -> {
                int amountHealed = player.heal(40);

                ui.displayMessage(
                        player.getName()
                                + " restored "
                                + amountHealed
                                + " health."
                );
            }

            case 2 -> {
                player.addPotion();

                ui.displayMessage(
                        player.getName()
                                + " received one potion."
                );
            }

            default -> throw new IllegalStateException(
                    "Unexpected reward choice: " + rewardChoice
            );
        }
    }

}