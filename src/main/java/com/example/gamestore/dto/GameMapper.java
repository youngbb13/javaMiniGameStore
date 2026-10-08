package com.example.gamestore.dto;

import com.example.gamestore.entity.DigitalGame;

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
