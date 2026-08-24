package com.example.gamestore;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class GameController {
    private final StoreService storeService;
    private final UserService userService;

    public GameController(StoreService storeService, UserService userService) {
        this.storeService = storeService;
        this.userService = userService;
    }

    @GetMapping("/games")
    public List<DigitalGame> getAllGames() {
        return storeService.getAllGames();
    }

    @GetMapping("/games/genre/{genre}")
    public List<DigitalGame> getGamesByGenre(@PathVariable Genre genre) {
        return storeService.findGamesByGenre(genre);
    }

    @GetMapping("/games/{title}")
    public ResponseEntity<DigitalGame> getGameByTitle(@PathVariable String title) {
        return storeService.findGameByTitle(title)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/games")
    public ResponseEntity<DigitalGame> addGame(@RequestBody DigitalGame game) {

        if (game.getTitle() == null || game.getTitle().isBlank())
            return ResponseEntity.badRequest().build();
        if (game.getPrice().compareTo(BigDecimal.ZERO) <= 0)
            return ResponseEntity.badRequest().build();

        storeService.addGameToCatalog(game);
        return ResponseEntity.ok(game);
    }

    @DeleteMapping("/games/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        boolean deleted = storeService.deleteGameById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/games/{title}")
    public ResponseEntity<DigitalGame> updateGame(@PathVariable String title, @RequestBody DigitalGame updatedGame) {

        if (updatedGame.getTitle() == null || updatedGame.getTitle().isBlank())
            return ResponseEntity.badRequest().build();
        if (updatedGame.getPrice().compareTo(BigDecimal.ZERO) <= 0)
            return ResponseEntity.badRequest().build();

        return storeService.updateGame(title, updatedGame)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/users/{nickname}/library")
    public ResponseEntity<?> getUserLibrary(@PathVariable String nickname) {
        return userService.findUserByNickname(nickname)
                .map(user -> ResponseEntity.ok(user.getGamesLibrary()))
                .orElse(ResponseEntity.notFound().build());
    }
}
