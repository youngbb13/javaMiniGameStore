public class Main {
    public static void main(String[] args) {
        Game gameCyberpunk = new DigitalGame("Cyberpunk 2077", 199.99, "RPG");
        Game gameCS2 = new DigitalGame("Counter-Strike 2", 49.99, "FPS");
        Game gameRDR2 = new DigitalGame("Red Dead Redemption 2", 199.99, "Action-Adventure");

        User dima = new User("kenzii" , 500);
        dima.addGame(gameCS2);
        dima.addGame(gameRDR2);

        System.out.println(dima.getNickname() + " owns: ");
        dima.showLibrary();
        System.out.println();

        StoreService store = new StoreService();
        try {
            store.buyGame(dima, gameCyberpunk);
            System.out.println(gameCyberpunk + " bought successfully!");
        }
        catch (NotEnoughMoneyException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(dima.getNickname() + " owns: ");
        dima.showLibrary();
    }
}
