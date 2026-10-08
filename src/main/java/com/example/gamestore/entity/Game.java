package com.example.gamestore.entity;

import java.math.BigDecimal;

public interface Game {
    String getTitle();
    Genre getGenre();
    BigDecimal getPrice();
    void play();
}
