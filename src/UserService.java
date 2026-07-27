public class UserService {
    public void tryBuy(StoreService store, User user, Game game) {
        try {
            store.buyGame(user, game);
            System.out.println(user.getNickname() + " bought successfully " + game + " for " + game.getPrice());
        }
        catch (GameAlreadyOwnedException | NotEnoughMoneyException e) {
            System.out.println(e.getMessage());
        }
    }
}
