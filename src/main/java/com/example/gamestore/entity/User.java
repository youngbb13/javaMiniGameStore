package com.example.gamestore.entity;

import com.example.gamestore.exception.InvalidAmountException;
import com.example.gamestore.exception.NotEnoughMoneyException;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected User() {

    }

    private String nickname;
    private BigDecimal balance;

    @ManyToMany
    @JoinTable(
            name = "user_games",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    private Set<DigitalGame> gamesLibrary = new HashSet<>();

    public Long getId() {
        return id;
    }

    public User(String nickname, BigDecimal balance) {
        this.nickname = nickname;
        this.balance = balance;
    }

    public Set<DigitalGame> getGamesLibrary() {
        return gamesLibrary;
    }

    public String getNickname() {
        return nickname;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void addGame(DigitalGame game) {
        gamesLibrary.add(game);
    }

    public void addBalance(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public void showLibrary() {
        for (DigitalGame games : gamesLibrary) {
            System.out.println(games.getTitle());
        }
    }

    public void playGame(String title) {
        for (DigitalGame game : gamesLibrary) {
            if (game.getTitle().equals(title)) {
                game.play();
                return;
            }
        }
        System.out.println("You don`t own this game!");
    }

    public synchronized void withdrawBalance(BigDecimal amount) throws NotEnoughMoneyException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new InvalidAmountException("Amount must be greater than 0");

        if (amount.compareTo(balance) > 0) throw new NotEnoughMoneyException("Not enough money");

        balance = balance.subtract(amount);
    }

    public boolean ownsGame(DigitalGame game) {
        return gamesLibrary.contains(game);
    }
}
