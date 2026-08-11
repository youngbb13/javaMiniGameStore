package com.example.gamestore;

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

        // Створюємо ігри
        Game cyberpunk = new DigitalGame("Cyberpunk 2077", new BigDecimal("159.99"), Genre.RPG);
        Game cs2 = new DigitalGame("Counter-Strike 2", new BigDecimal("39.99"), Genre.FPS);

        // Додаємо в каталог
        storeService.addGameToCatalog(cyberpunk);
        storeService.addGameToCatalog(cs2);

        // Створюємо користувача
        User dima = new User("kenzii", new BigDecimal("1000"));

        // Купуємо гру через UserService
        userService.tryBuy(dima, cs2);

        // Показуємо результат
        System.out.println("Balance: " + dima.getBalance());
        System.out.println("Library:");
        dima.showLibrary();
    }
}
