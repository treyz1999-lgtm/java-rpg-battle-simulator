package com.ltd.rpg.combat;

import com.ltd.rpg.character.Enemy;
import com.ltd.rpg.character.Player;

/**
 * Coordinates turn-based combat operations.
 *
 * <p>This service processes player actions, performs enemy turns,
 * and determines when a battle has ended.</p>
 *
 * <p>The service contains combat coordination logic but does not
 * handle keyboard input or presentation.</p>
 */
public class BattleService {

    /**
     * Processes one player action.
     *
     * @param player player performing the action
     * @param enemy enemy involved in the battle
     * @param action selected action type
     * @return result of the selected action
     */
    public ActionResult processPlayerAction(
            Player player,
            Enemy enemy,
            ActionType action
    ) {
        return switch (action) {
            case BASIC_ATTACK ->
                    player.performBasicAttack(enemy);

            case SPECIAL_ABILITY ->
                    player.useSpecialAbility(enemy);

            case USE_POTION ->
                    player.usePotion();
        };
    }

    /**
     * Processes an enemy's basic attack against the player.
     *
     * @param enemy attacking enemy
     * @param player player receiving the attack
     * @return result of the enemy action
     */
    public ActionResult processEnemyTurn(
            Enemy enemy,
            Player player
    ) {
        return enemy.performBasicAttack(player);
    }

    /**
     * Determines whether either participant has been defeated.
     *
     * @param player current player
     * @param enemy current enemy
     * @return true when the player or enemy is no longer alive
     */
    public boolean isBattleOver(
            Player player,
            Enemy enemy
    ) {
        return !player.isAlive() || !enemy.isAlive();
    }
}