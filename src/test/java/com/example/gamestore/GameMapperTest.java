package com.example.gamestore;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameMapperTest {

    @Test
    void toDto_mapsAllFields() {
        DigitalGame game = new DigitalGame("Elden Ring", new BigDecimal("59.99"), Genre.RPG);

        GameDto dto = GameMapper.toDto(game);

        assertEquals("Elden Ring", dto.getTitle());
        assertEquals(new BigDecimal("59.99"), dto.getPrice());
        assertEquals(Genre.RPG, dto.getGenre());
    }
}