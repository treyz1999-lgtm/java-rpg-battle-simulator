package com.ltd.rpg.controller;

import com.ltd.rpg.dto.CreateGameRequest;
import com.ltd.rpg.dto.GameStateResponse;
import com.ltd.rpg.dto.PlayerActionRequest;
import com.ltd.rpg.dto.RewardRequest;
import com.ltd.rpg.dto.TurnResponse;
import com.ltd.rpg.service.GameService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Exposes HTTP endpoints for game creation and progression.
 */
@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public GameStateResponse createGame(
            @RequestBody CreateGameRequest request
    ) {
        return gameService.createGame(
                request.playerName(),
                request.characterClass()
        );
    }

    @GetMapping("/{gameId}")
    public GameStateResponse getGame(
            @PathVariable UUID gameId
    ) {
        return gameService.getGame(gameId);
    }

    @PostMapping("/{gameId}/actions")
    public TurnResponse performAction(
            @PathVariable UUID gameId,
            @RequestBody PlayerActionRequest request
    ) {
        return gameService.processAction(
                gameId,
                request.action()
        );
    }

    @PostMapping("/{gameId}/rewards")
    public GameStateResponse selectReward(
            @PathVariable UUID gameId,
            @RequestBody RewardRequest request
    ) {
        return gameService.selectReward(
                gameId,
                request.reward()
        );
    }
}