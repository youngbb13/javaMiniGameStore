public class GameDownloader implements Runnable {
    private Game game;

    public GameDownloader(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        for (int i = 10; i <= 100; i += 10) {
            System.out.println("Downloading " + game + "... " + i + "%");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Downloading interrupted!");
            }

        }
        System.out.println("Game installed");
    }
}
