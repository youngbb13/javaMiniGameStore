package com.example.gamestore.exception;

public class GameAlreadyOwnedException extends Exception {
    public GameAlreadyOwnedException(String message) {
        super(message);
    }
}
