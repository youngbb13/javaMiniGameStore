package com.example.gamestore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "games")
public class DigitalGame implements Game, Comparable<Game> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotNull
    @Positive
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    // Обов'язковий порожній конструктор для JPA
    protected DigitalGame() {

    }

    @JsonIgnore
    @ManyToMany(mappedBy = "gamesLibrary")
    private Set<User> owners = new HashSet<>();

    public DigitalGame(String title, BigDecimal price, Genre genre) {
        this.title = title;
        this.price = price;
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public Genre getGenre() {
        return genre;
    }

    @Override
    public void play() {
        System.out.println("Playing " + getTitle());
    }

    @Override
    public String toString() {
        return  title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        else if (o == null || getClass() != o.getClass())
            return false;

        DigitalGame game = (DigitalGame) o;
        return title.equals(game.title);
    }

    @Override
    public int hashCode() {
        return title != null ? title.hashCode() : 0;
    }

    @Override
    public int compareTo(Game o) {
        return this.getPrice().compareTo(o.getPrice());
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
