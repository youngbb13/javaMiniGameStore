package com.example.gamestore;

public class UserMapper {
    public static UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getNickname(),
                user.getBalance()
        );
    }
}
