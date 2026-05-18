public class StoreService {
    public void buyGame(User user, Game game) throws NotEnoughMoneyException, GameAlreadyOwnedException {
        if (user.ownsGame(game)) throw new GameAlreadyOwnedException("You already own " + game.getTitle());

        if (user.getBalance().compareTo(game.getPrice()) < 0) throw new NotEnoughMoneyException("Not enough money!");

        user.withdrawBalance(game.getPrice());
        user.addGame(game);
    }
}
