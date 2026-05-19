import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Game gameCyberpunk = new DigitalGame("Cyberpunk 2077", new BigDecimal("159.99"), "RPG");
        Game gameCS2 = new DigitalGame("Counter-Strike 2", new BigDecimal("39.99"), "FPS");
        Game gameRDR2 = new DigitalGame("Red Dead Redemption 2", new BigDecimal("199.99"), "Action-Adventure");
        Game gameValheim = new DigitalGame("Valheim", new BigDecimal("70.99"), "Survival");

        StoreService store = new StoreService();

        store.addGameToCatalog(gameCyberpunk);
        store.addGameToCatalog(gameCS2);
        store.addGameToCatalog(gameRDR2);
        store.addGameToCatalog(gameValheim);

        User dima = new User("kenzii" , new BigDecimal("1000"));
        User valentyn = new User("Black_Ghost", new BigDecimal("200"));

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

        Optional<Game> foundGame = store.findGameByTitle("Valheim");
        foundGame.ifPresentOrElse(game -> System.out.println("Found game: " + game),
                () -> System.out.println("Game not found"));

        System.out.println();

        List<String> titles = store.getAllGameTitles();
        System.out.println("All game titles:");
        for (String title : titles) {
            System.out.println(title);
        }

        System.out.println();

        List<Game> sortedGames = store.sortGamesByPrice();
        System.out.println("Sorted games(growing):");
        for (Game game : sortedGames) {
            System.out.println(game.getTitle() + " - " + game.getPrice());
        }

        GameLibrary<Game> library = new GameLibrary<>();
        System.out.println();
        library.add(gameCyberpunk);
        library.add(gameRDR2);
        library.showAllGames();

        System.out.println();

        Game firstGame = library.getFirst();
        System.out.println(firstGame);

        System.out.println();

        System.out.println("before " + valentyn.getBalance());
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(() -> {
            tryBuy(store, valentyn, gameCS2);
        });
        executor.submit(() -> {
            tryBuy(store, valentyn, gameValheim);
        });
        executor.shutdown();

        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted!");
        }

        System.out.println("after " + valentyn.getBalance());
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