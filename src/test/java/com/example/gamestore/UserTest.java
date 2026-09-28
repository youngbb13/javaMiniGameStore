package com.example.gamestore;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void withdrawBalance_notEnoughMoney_throwsException() {
        User user = new User("kenzii", new BigDecimal("10"));

        assertThrows(NotEnoughMoneyException.class, () -> {
            user.withdrawBalance(new BigDecimal("50"));
        });
    }

    @Test
    void withdrawBalance_enoughMoney_decreasesBalance() throws Exception {
        User user = new User("kenzii", new BigDecimal("100"));

        user.withdrawBalance(new BigDecimal("40"));

        assertEquals(new BigDecimal("60"), user.getBalance());
    }

    @Test
    void addBalance_increasesBalance() {
        User user = new User("kenzii", new BigDecimal("100"));

        user.addBalance(new BigDecimal("50"));

        assertEquals(new BigDecimal("150"), user.getBalance());
    }

    @Test
    void addBalance_invalidAmount_throwsException() {
        User user = new User("kenzii", new BigDecimal("100"));

        assertThrows(InvalidAmountException.class, () -> {
            user.addBalance(BigDecimal.ZERO);
        });
    }
}