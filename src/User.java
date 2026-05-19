import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class User {
    private final String nickname;
    private BigDecimal balance;
    private final Set<Game> gamesLibrary = new HashSet<>();

    public User(String nickname, BigDecimal balance) {
        this.nickname = nickname;
        this.balance = balance;
    }

    public String getNickname() {
        return nickname;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    void addGame(Game game) {
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

    public synchronized void withdrawBalance(BigDecimal amount) throws NotEnoughMoneyException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new InvalidAmountException("Amount must be greater than 0");

        if (amount.compareTo(balance) > 0) throw new NotEnoughMoneyException("Not enough money");

        balance = balance.subtract(amount);
    }

    public boolean ownsGame(Game game) {
        return gamesLibrary.contains(game);
    }
}
