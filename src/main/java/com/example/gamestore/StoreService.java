package com.example.gamestore;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class StoreService {
    public void buyGame(User user, Game game) throws NotEnoughMoneyException, GameAlreadyOwnedException {
        if (user.ownsGame(game)) throw new GameAlreadyOwnedException("You already own " + game.getTitle());

        if (user.getBalance().compareTo(game.getPrice()) < 0) throw new NotEnoughMoneyException("Not enough money!");

        user.withdrawBalance(game.getPrice());
        user.addGame(game);
    }

    private final List<Game> catalogOfGames = new ArrayList<>();
    private final Map<String, Game> gamesByTitle = new HashMap<>();

    public void addGameToCatalog(Game game) {
        gamesByTitle.put(game.getTitle(), game);
        catalogOfGames.add(game);
    }

    public void showCatalog() {
        for (Game games : catalogOfGames) {
            System.out.println(games.getTitle());
        }
    }

    public Optional<Game> findGameByTitle(String title) {
        return catalogOfGames.stream()
                .filter(game -> game.getTitle().equalsIgnoreCase(title))
                .findFirst();
    }

    public List<Game> findGamesByGenre(Genre genre) {
        return catalogOfGames.stream()
                .filter(game -> game.getGenre().equals(genre))
                .collect(Collectors.toList());
    }

    public List<String> getAllGameTitles() {
        return catalogOfGames.stream()
                .map(Game::getTitle)
                .collect(Collectors.toList());
    }

    public List<Game> sortGamesByPrice() {
        return catalogOfGames.stream()
                .sorted(Comparator.comparing(Game::getPrice))
                .collect(Collectors.toList());
    }

    public Optional<Game> findGameByTitleFast(String title) {
        return Optional.ofNullable(gamesByTitle.get(title));
    }

    public int countGamesMoreExpensiveThan(BigDecimal price) {
        return (int) catalogOfGames.stream()
                .filter(game -> game.getPrice().compareTo(price) > 0)
                .count();
    }

    public Optional<Game> getMostExpensiveGame() {
        return catalogOfGames.stream()
                .filter(game -> game.getPrice() != null)
                .max(Comparator.comparing(Game::getPrice));
    }

    public Map<Genre, Long> countGamesByGenre() {
        return catalogOfGames.stream()
                .collect(Collectors.groupingBy(Game::getGenre, Collectors.counting()));
    }

    public Map<Genre, List<Game>> getGamesGroupedByGenre() {
        return catalogOfGames.stream()
                .collect(Collectors.groupingBy(Game::getGenre));
    }

    public List<Game> sortGamesByTitle() {
        return catalogOfGames.stream()
                .sorted(Comparator.comparing(Game::getTitle))
                .collect(Collectors.toList());
    }

    public List<Game> sortGamesByPriceDescending() {
        return catalogOfGames.stream()
                .sorted(Comparator.comparing(Game::getPrice).reversed())
                .collect(Collectors.toList());
    }

    public List<Game> findGamesByCondition(Predicate<Game> condition) {
        return catalogOfGames.stream()
                .filter(condition)
                .collect(Collectors.toList());
    }

    public OptionalDouble getAveragePriceByGenre(Genre genre) {
        return catalogOfGames.stream()
                .filter(game -> game.getGenre().equals(genre))
                .mapToDouble(game -> game.getPrice().doubleValue()).average();
    }

    public List<Game> getTopMostExpensiveGames(int limit) {
        return catalogOfGames.stream()
                .sorted(Comparator.comparing(Game::getPrice).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    public Optional<Genre> getMostPopularGenre() {
        Map<Genre, Long> countByGenre = catalogOfGames.stream()
                .collect(Collectors.groupingBy(Game::getGenre, Collectors.counting()));
        return countByGenre.entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey);
    }

    public List<Game> getAllGames() {
        return catalogOfGames;
    }
}
