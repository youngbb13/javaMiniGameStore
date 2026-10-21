package com.example.gamestore.controller;

import com.example.gamestore.exception.GameAlreadyOwnedException;
import com.example.gamestore.exception.NotEnoughMoneyException;
import com.example.gamestore.entity.User;
import com.example.gamestore.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
public class PurchaseController {

    private final UserService userService;

    public PurchaseController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/buy")
    public ResponseEntity<String> buyGame(@RequestParam String nickname, @RequestParam String gameTitle) throws NotEnoughMoneyException, GameAlreadyOwnedException {

        User user = userService.buyGame(nickname, gameTitle);

        return ResponseEntity.ok(user.getNickname() + " bought " + gameTitle
                + ". Balance: " + user.getBalance());
    }
}