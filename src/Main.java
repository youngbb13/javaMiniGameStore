import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Game gameCyberpunk = new DigitalGame("Cyberpunk 2077", new BigDecimal("199.99"), "RPG");
        Game gameCS2 = new DigitalGame("Counter-Strike 2", new BigDecimal("29.99"), "FPS");
        Game gameRDR2 = new DigitalGame("Red Dead Redemption 2", new BigDecimal("199.99"), "Action-Adventure");
        Game gameValheim = new DigitalGame("Valheim", new BigDecimal("70.99"), "Survival");

        StoreService store = new StoreService();

        User dima = new User("kenzii" , new BigDecimal("1000"));

        System.out.println(dima.getNickname() + " balance is: " + dima.getBalance());
        System.out.println(dima.getNickname() + " owns: ");
        dima.showLibrary();

        System.out.println();

        tryBuy(store, dima, gameCS2);
        tryBuy(store, dima, gameRDR2);
        tryBuy(store, dima, gameValheim);

        System.out.println(dima.getNickname() + " owns: ");
        dima.showLibrary();

        System.out.println();
        System.out.println(dima.getNickname() + " balance is: " + dima.getBalance());
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
