package com.ltd.rpg.ui;

import com.ltd.rpg.combat.ActionResult;

import java.util.Scanner;

import java.util.Scanner;

public class ConsoleUI {

    private final Scanner keyboard;

    public ConsoleUI() {
        this.keyboard = new Scanner(System.in);
    }

    public String askForName() {
        System.out.print("Enter your character's name: ");
        return keyboard.nextLine().trim();
    }

    public int chooseCharacter() {
        System.out.println();
        System.out.println("Choose your class");
        System.out.println("-----------------");
        System.out.println("1. Warrior");
        System.out.println("2. Mage");
        System.out.println("3. Rogue");

        return readChoice(1, 3);
    }

    public int chooseBattleAction() {
        System.out.println();
        System.out.println("Choose an action");
        System.out.println("----------------");
        System.out.println("1. Basic Attack");
        System.out.println("2. Special Ability");
        System.out.println("3. Use Potion");

        return readChoice(1, 3);
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    private int readChoice(int minimum, int maximum) {
        while (true) {
            System.out.print("> ");
            String input = keyboard.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);

                if (choice >= minimum && choice <= maximum) {
                    return choice;
                }

                System.out.println(
                        "Please enter a number from "
                                + minimum
                                + " to "
                                + maximum
                                + "."
                );
            }
            catch (NumberFormatException exception) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public void displayActionResult(ActionResult result) {
        if (!result.successful()) {
            System.out.println(
                    result.actorName()
                            + " could not use "
                            + result.actionName()
                            + "."
            );

            return;
        }

        if (result.damageDealt() > 0) {
            String criticalMessage = result.criticalHit()
                    ? " Critical hit!"
                    : "";

            System.out.println(
                    result.actorName()
                            + " used "
                            + result.actionName()
                            + " on "
                            + result.targetName()
                            + " for "
                            + result.damageDealt()
                            + " damage."
                            + criticalMessage
            );
        }

        if (result.healingDone() > 0) {
            System.out.println(
                    result.actorName()
                            + " used "
                            + result.actionName()
                            + " and restored "
                            + result.healingDone()
                            + " health."
            );
        }
    }

    public void close() {
        keyboard.close();
    }
}