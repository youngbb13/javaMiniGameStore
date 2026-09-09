package com.example.gamestore;

public class GameMapper {
    public static GameDto toDto(DigitalGame game) {
        return new GameDto(
                game.getId(),
                game.getTitle(),
                game.getPrice(),
                game.getGenre()
        );
    }
}
