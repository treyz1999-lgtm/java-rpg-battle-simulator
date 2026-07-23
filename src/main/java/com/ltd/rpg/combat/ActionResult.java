package com.ltd.rpg.combat;

public record ActionResult(
        String actorName,
        String actionName,
        String targetName,
        int damageDealt,
        int healingDone,
        boolean criticalHit,
        boolean successful
) {

}