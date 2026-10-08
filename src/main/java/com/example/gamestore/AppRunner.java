package com.example.gamestore;

import com.example.gamestore.entity.DigitalGame;
import com.example.gamestore.entity.Genre;
import com.example.gamestore.entity.User;
import com.example.gamestore.service.StoreService;
import com.example.gamestore.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AppRunner implements CommandLineRunner {
    private final StoreService storeService;
    private final UserService userService;

    public AppRunner(StoreService storeService, UserService userService) {
        this.storeService = storeService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Spring Boot Game Store");

        if (!storeService.getAllGames().isEmpty()) {
            System.out.println("Data already exists, skip loading");
            return;
        }

        // Створюємо ігри
        DigitalGame cyberpunk = new DigitalGame("Cyberpunk 2077", new BigDecimal("159.99"), Genre.RPG);
        DigitalGame cs2 = new DigitalGame("Counter-Strike 2", new BigDecimal("39.99"), Genre.FPS);
        DigitalGame eldenRing = new DigitalGame("Elden Ring", new BigDecimal("59.99"), Genre.RPG);

        // Додаємо в каталог
        storeService.addGameToCatalog(cyberpunk);
        storeService.addGameToCatalog(cs2);
        storeService.addGameToCatalog(eldenRing);

        // Створюємо користувача
        if (userService.findUserByNickname("kenzii").isEmpty()) {
            User dima = new User("kenzii", new BigDecimal("500"));
            userService.addUser(dima);
        }

        System.out.println("Initial data loaded");
    }
}
