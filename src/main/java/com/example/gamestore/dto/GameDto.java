package com.example.gamestore.dto;

import com.example.gamestore.entity.Genre;

import java.math.BigDecimal;

public class GameDto {
    private Long id;
    private String title;
    private BigDecimal price;
    private Genre genre;

    public GameDto(Long id, String title, BigDecimal price, Genre genre) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Genre getGenre() {
        return genre;
    }

    public Long getId() {
        return id;
    }
}
