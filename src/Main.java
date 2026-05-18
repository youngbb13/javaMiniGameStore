import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Game gameCyberpunk = new DigitalGame("Cyberpunk 2077", new BigDecimal("199.99"), "RPG");
        Game gameCS2 = new DigitalGame("Counter-Strike 2", new BigDecimal("29.99"), "FPS");
        Game gameRDR2 = new DigitalGame("Red Dead Redemption 2", new BigDecimal("199.99"), "Action-Adventure");
        Game gameValheim = new DigitalGame("Valheim", new BigDecimal("70.99"), "Survival");

        StoreService store = new StoreService();

        store.addGameToCatalog(gameCyberpunk);
        store.addGameToCatalog(gameCS2);
        store.addGameToCatalog(gameRDR2);
        store.addGameToCatalog(gameValheim);

        User dima = new User("kenzii" , new BigDecimal("1000"));

        System.out.println(dima.getNickname() + " balance is: " + dima.getBalance());
        System.out.println(dima.getNickname() + " owns: ");
        dima.showLibrary();
        System.out.println();

        System.out.println("Store catalog: ");
        store.showCatalog();
        System.out.println();

        tryBuy(store, dima, gameCS2);
        tryBuy(store, dima, gameRDR2);
        tryBuy(store, dima, gameValheim);
        System.out.println();

        System.out.println(dima.getNickname() + " owns: ");
        dima.showLibrary();

        System.out.println(dima.getNickname() + " balance is: " + dima.getBalance());
        System.out.println();

        List<Game> fpsGames = store.findGamesByGenre("FPS");

        System.out.println("FPS games:");
        for (Game game : fpsGames) {
            System.out.println(game.getTitle());
        }
        System.out.println();

        Game foundGame = store.findGameByTitle("Valheim");
        if (foundGame != null) System.out.println("Found game: " + foundGame);
        else System.out.println("Game not found");

        System.out.println();

        List<String> titles = store.getAllGameTitles();

        System.out.println("All game titles:");
        for (String title : titles) {
            System.out.println(title);
        }
    }

    private static void tryBuy(StoreService store, User user, Game game) {
        try {
            store.buyGame(user, game);
            System.out.println(user.getNickname() + " bought successfully " + game + " for " + game.getPrice());
        }
        catch (GameAlreadyOwnedException | NotEnoughMoneyException e) {
            System.out.println(e.getMessage());
        }
    }
}
