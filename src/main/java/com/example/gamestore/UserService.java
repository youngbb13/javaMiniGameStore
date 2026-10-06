package com.example.gamestore;

import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public void tryBuy(User user, DigitalGame game) throws NotEnoughMoneyException, GameAlreadyOwnedException {
            storeService.buyGame(user, game);
            userRepository.save(user);
    }
}
