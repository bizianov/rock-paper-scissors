package app.controller;

import app.model.Move;
import app.model.Result;
import app.model.Score;
import app.service.game.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping(value = "/game/new")
    public ResponseEntity<Score> startGame() {
        return ResponseEntity.ok(gameService.startGame());
    }

    @GetMapping(value = "/game/turn/{move}")
    public ResponseEntity<Result> nextTurn(@PathVariable("move") String moveName) {
        return ResponseEntity.ok(gameService.nextTurn(Move.valueOf(moveName.toUpperCase())));
    }

    @GetMapping(value = "/game/stop")
    public ResponseEntity<Score> stopGame() {
        return ResponseEntity.ok(gameService.stopGame());
    }
}