package com.example.gamestore.dto;

import com.example.gamestore.entity.User;

public class UserMapper {
    public static UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getNickname(),
                user.getBalance()
        );
    }
}
