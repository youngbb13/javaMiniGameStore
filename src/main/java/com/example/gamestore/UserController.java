package com.example.gamestore;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<?> addNewUser(@RequestParam String nickname, @RequestParam BigDecimal balance) {
        if (userService.findUserByNickname(nickname).isPresent()) {
            return ResponseEntity.badRequest().body("User already exists!");
        }
        User user = new User(nickname, balance);
        userService.addUser(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/users/{nickname}/funds")
    public ResponseEntity<?> addFundsToBalance(@PathVariable String nickname, @RequestParam BigDecimal amount) {
        try {
            return userService.addFunds(nickname, amount)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (InvalidAmountException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
