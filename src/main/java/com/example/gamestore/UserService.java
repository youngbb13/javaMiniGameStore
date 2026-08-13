package com.example.gamestore;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final StoreService storeService;
    private final List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public Optional<User> findUserByNickname(String nickname) {
        return users.stream()
                .filter(user -> user.getNickname().equalsIgnoreCase(nickname))
                .findFirst();
    }

    public UserService(StoreService storeService) {
        this.storeService = storeService;
    }

//    public void tryBuy(User user, Game game) {
//        try {
//            storeService.buyGame(user, game);
//            System.out.println(user.getNickname() + " bought successfully " + game + " for " + game.getPrice());
//        }
//        catch (GameAlreadyOwnedException | NotEnoughMoneyException e) {
//            System.out.println(e.getMessage());
//        }
//    }
    public String tryBuy(User user, Game game) {
        try {
            storeService.buyGame(user, game);
            return "Successfully bought " + game.getTitle();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
