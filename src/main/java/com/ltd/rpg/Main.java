package com.ltd.rpg;

/**
 * Entry point for the Realm of Java application.
 *
 * <p>This class creates a game instance and begins the game.
 * Application logic is intentionally kept outside the main method.</p>
 */

public class Main {
    /**
     * Starts the application.
     *
     * @param args command-line arguments passed to the program
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}