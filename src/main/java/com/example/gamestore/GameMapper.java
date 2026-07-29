package com.example.gamestore;

public class GameMapper {
    public static GameDto toDto(Game game) {
        return new GameDto(
                game.getTitle(),
                game.getPrice(),
                game.getGenre()
        );
    }
}
