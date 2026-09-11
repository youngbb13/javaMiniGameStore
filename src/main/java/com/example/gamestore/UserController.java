package com.example.gamestore;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/users/{nickname}/library")
    public ResponseEntity<?> getUserLibrary(@PathVariable String nickname) {
        return userService.findUserByNickname(nickname)
                .map(user -> ResponseEntity.ok(user.getGamesLibrary()))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/users/{nickname}/balance")
    public ResponseEntity<?> getUserBalance(@PathVariable String nickname) {
        return userService.findUserByNickname(nickname)
                .map(user -> ResponseEntity.ok(user.getBalance()))
                .orElse(ResponseEntity.notFound().build());
    }
}
