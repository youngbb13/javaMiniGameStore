package com.example.gamestore;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class PurchaseController {

    private final StoreService storeService;
    private final UserService userService;

    public PurchaseController(StoreService storeService, UserService userService) {
        this.storeService = storeService;
        this.userService = userService;
    }

    @PostMapping("/buy")
    public ResponseEntity<String> buyGame(@RequestParam String nickname, @RequestParam String gameTitle) {

        Optional<User> userOpt = userService.findUserByNickname(nickname);
        Optional<Game> gameOpt = storeService.findGameByTitle(gameTitle);

        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found!");
        }
        if (gameOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Game not found!");
        }

        User user = userOpt.get();
        Game game = gameOpt.get();

        String result = userService.tryBuy(user, game);

        if (result.startsWith("Successfully")) return ResponseEntity.ok(result);
        else return ResponseEntity.badRequest().body(result);

    }

    @GetMapping("/users/{nickname}/balance")
    public ResponseEntity<?> getUserBalance(@PathVariable String nickname) {
        return userService.findUserByNickname(nickname)
                .map(user -> ResponseEntity.ok(user.getBalance()))
                .orElse(ResponseEntity.notFound().build());
    }
}