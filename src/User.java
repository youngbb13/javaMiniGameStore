import java.util.HashSet;
import java.util.Set;

public class User {
    private String nickname;
    private double balance;
    private Set<Game> gamesLibrary = new HashSet<>();

    public User(String nickname, double balance) {
        this.nickname = nickname;
        this.balance = balance;
    }

    public String getNickname() {
        return nickname;
    }

    public double getBalance() {
        return balance;
    }

    public void addGame(Game game) {
        gamesLibrary.add(game);
    }

    public void showLibrary() {
        for (Game games : gamesLibrary) {
            System.out.println(games.getTitle());
        }
    }

    public void playGame(String title) {
        for (Game game : gamesLibrary) {
            if (game.getTitle().equals(title)) {
                game.play();
                return;
            }
        }
        System.out.println("You don`t own this game!");
    }

    public void withdrawBalance(double amount) throws NotEnoughMoneyException {
        if (amount > balance) throw new NotEnoughMoneyException("Not enough money");
        else {
            balance -= amount;
        }
    }
}
