package com.example.gamestore;

public class GameAlreadyOwnedException extends Exception {
    public GameAlreadyOwnedException(String message) {
        super(message);
    }
}
