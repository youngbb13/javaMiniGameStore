package com.example.gamestore.controller;

import com.example.gamestore.dto.GameDto;
import com.example.gamestore.dto.GameMapper;
import com.example.gamestore.entity.DigitalGame;
import com.example.gamestore.entity.Genre;
import com.example.gamestore.service.StoreService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class GameController {
    private final StoreService storeService;

    public GameController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/games")
    public List<GameDto> getAllGames(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return storeService.getAllGames(page, size)
                .stream()
                .map(GameMapper::toDto)
                .toList();
    }

    @GetMapping("/games/genre/{genre}")
    public List<GameDto> getGamesByGenre(@PathVariable Genre genre) {
        return storeService.findGamesByGenre(genre)
                .stream()
                .map(GameMapper::toDto)
                .toList();
    }

    @GetMapping("/games/{title}")
    public ResponseEntity<GameDto> getGameByTitle(@PathVariable String title) {
        return storeService.findGameByTitle(title)
                .map(GameMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/games")
    public ResponseEntity<DigitalGame> addGame(@Valid @RequestBody DigitalGame game) {
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

    @GetMapping("/games/search")
    public List<GameDto> searchGames(@RequestParam String title) {
        return storeService.findGameByHalfTitleIgnoreCase(title)
                .stream()
                .map(GameMapper::toDto)
                .toList();
    }
}
