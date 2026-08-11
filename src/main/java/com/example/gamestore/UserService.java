package com.example.gamestore;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final StoreService storeService;
    public UserService(StoreService storeService) {
        this.storeService = storeService;
    }

    public void tryBuy(User user, Game game) {
        try {
            storeService.buyGame(user, game);
            System.out.println(user.getNickname() + " bought successfully " + game + " for " + game.getPrice());
        }
        catch (GameAlreadyOwnedException | NotEnoughMoneyException e) {
            System.out.println(e.getMessage());
        }
    }
}
