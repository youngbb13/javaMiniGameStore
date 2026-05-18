import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StoreService {
    public void buyGame(User user, Game game) throws NotEnoughMoneyException, GameAlreadyOwnedException {
        if (user.ownsGame(game)) throw new GameAlreadyOwnedException("You already own " + game.getTitle());

        if (user.getBalance().compareTo(game.getPrice()) < 0) throw new NotEnoughMoneyException("Not enough money!");

        user.withdrawBalance(game.getPrice());
        user.addGame(game);
    }

    private List<Game> catalogOfGames = new ArrayList<>();

    public void addGameToCatalog(Game game) {
        catalogOfGames.add(game);
    }

    public void showCatalog() {
        for (Game games : catalogOfGames) {
            System.out.println(games.getTitle());
        }
    }

    public void showGamesByGenre(String genre) {
        catalogOfGames.stream()
                .filter(game -> game.getGenre().equalsIgnoreCase(genre))
                .forEach(game -> System.out.println(game.getTitle()));
    }

    public Game findGameByTitle(String title) {
        return catalogOfGames.stream()
                .filter(game -> game.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    public List<Game> findGamesByGenre(String genre) {
        return catalogOfGames.stream()
                .filter(game -> game.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }
}
