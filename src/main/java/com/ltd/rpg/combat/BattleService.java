package com.ltd.rpg.combat;

import com.ltd.rpg.character.Enemy;
import com.ltd.rpg.character.Player;

public class BattleService {

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

    public ActionResult processEnemyTurn(
            Enemy enemy,
            Player player
    ) {
        return enemy.performBasicAttack(player);
    }

    public boolean isBattleOver(
            Player player,
            Enemy enemy
    ) {
        return !player.isAlive() || !enemy.isAlive();
    }
}