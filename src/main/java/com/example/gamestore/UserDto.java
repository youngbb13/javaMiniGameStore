package com.example.gamestore;

import java.math.BigDecimal;

public class UserDto {
    private Long id;
    private String nickname;
    private BigDecimal balance;

    public UserDto(Long id, String nickname, BigDecimal balance) {
        this.id = id;
        this.nickname = nickname;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
