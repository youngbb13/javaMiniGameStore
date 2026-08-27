package com.example.gamestore;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UserService {
    private final StoreService storeService;
    private final UserRepository userRepository;

    public UserService(StoreService storeService, UserRepository userRepository) {
        this.storeService = storeService;
        this.userRepository = userRepository;
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> addFunds(String nickname, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Amount must be greater than 0");
        }

        return findUserByNickname(nickname)
                .map(user -> {user.addBalance(amount);
                return userRepository.save(user);
                });
    }

    public Optional<User> findUserByNickname(String nickname) {
        return userRepository.findByNicknameIgnoreCase(nickname);
    }

    public String tryBuy(User user, DigitalGame game) {
        try {
            storeService.buyGame(user, game);
            userRepository.save(user);
            return "Successfully bought " + game.getTitle();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
