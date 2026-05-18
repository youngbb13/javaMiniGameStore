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

        try {
            store.buyGame(dima, gameCyberpunk);
            System.out.println(dima.getNickname() + " bought successfully " + gameCyberpunk + " for " + gameCyberpunk.getPrice());
        } catch (GameAlreadyOwnedException | NotEnoughMoneyException e) {
            System.out.println(e.getMessage());
        }

        try {
            store.buyGame(dima, gameValheim);
            System.out.println(dima.getNickname() + " bought successfully " + gameValheim + " for " + gameValheim.getPrice());
        } catch (GameAlreadyOwnedException | NotEnoughMoneyException e) {
            System.out.println(e.getMessage());
        }

        try {
            store.buyGame(dima, gameCS2);
            System.out.println(dima.getNickname() + " bought successfully " + gameCS2 + " for " + gameCS2.getPrice());
        } catch (GameAlreadyOwnedException | NotEnoughMoneyException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(dima.getNickname() + " owns: ");
        dima.showLibrary();
        System.out.println();
        System.out.println(dima.getNickname() + " balance is: " + dima.getBalance());
    }
}
