package com.example.gamestore;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GameController {
    private final StoreService storeService;

    public GameController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/games")
    public List<Game> getAllGames() {
        return storeService.getAllGames();
    }

    @GetMapping("/games/genre/{genre}")
    public List<Game> getGamesByGenre(@PathVariable Genre genre) {
        return storeService.findGamesByGenre(genre);
    }

    @GetMapping("/games/{title}")
    public ResponseEntity<Game> getGameByTitle(@PathVariable String title) {
        return storeService.findGameByTitle(title)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/games")
    public Game addGame(@RequestBody DigitalGame game) {
        storeService.addGameToCatalog(game);
        return game;
    }

    @DeleteMapping("/games/{title}")
    public ResponseEntity<Void> deleteGame(@PathVariable String title) {
        boolean deleted = storeService.deleteGameByTitle(title);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/games/{title}")
    public ResponseEntity<Game> updateGame(@PathVariable String title, @RequestBody DigitalGame updatedGame) {
        return storeService.updateGame(title, updatedGame)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/buy")
    public ResponseEntity<String> buyGame(@RequestParam String user, @RequestParam String gameTitle) {
        return ResponseEntity.ok("WiP!");
    }
}
