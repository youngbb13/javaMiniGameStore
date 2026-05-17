public class StoreService {
    public void buyGame(User user, Game game) throws NotEnoughMoneyException {
        if (user.getBalance() < game.getPrice()) throw new NotEnoughMoneyException("Not enough money!");
        else {
            user.withdrawBalance(game.getPrice());
            user.addGame(game);
        }
    }
}
