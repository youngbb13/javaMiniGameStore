package com.example.gamestore;

import java.math.BigDecimal;

public interface Game {
    String getTitle();
    Genre getGenre();
    BigDecimal getPrice();
    void play();
}
