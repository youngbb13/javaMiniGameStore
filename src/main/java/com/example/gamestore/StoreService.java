package com.example.gamestore;

import jakarta.persistence.Id;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;

import java.util.stream.Collectors;

@Service
public class StoreService {
    private final GameRepository gameRepository;

    public StoreService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public boolean buyGame(User user, Game game) throws NotEnoughMoneyException, GameAlreadyOwnedException {
        if (user.ownsGame(game)) throw new GameAlreadyOwnedException("You already own " + game.getTitle());

        if (user.getBalance().compareTo(game.getPrice()) < 0) throw new NotEnoughMoneyException("Not enough money!");

        user.withdrawBalance(game.getPrice());
        user.addGame(game);
        return true;
    }

    public DigitalGame addGameToCatalog(DigitalGame game) {
        return gameRepository.save(game);
    }

    public List<DigitalGame> findGamesByGenre(Genre genre) {
        return gameRepository.findByGenre(genre);
    }

    public List<DigitalGame> getAllGames() {
        return gameRepository.findAll();
    }

    public Optional<DigitalGame> findGameByTitle(String title) {
        return gameRepository.findByTitleIgnoreCase(title);
    }

    public boolean deleteGameById(Long id) {
        if (gameRepository.existsById(id)) {
            gameRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<DigitalGame> updateGame(String title, DigitalGame updatedGame) {
        return gameRepository.findByTitleIgnoreCase(title)
                .map(existingGame -> {
                    existingGame.setTitle(updatedGame.getTitle());
                    existingGame.setPrice(updatedGame.getPrice());
                    existingGame.setGenre(updatedGame.getGenre());
                    return gameRepository.save(existingGame);
                });
    }
}
