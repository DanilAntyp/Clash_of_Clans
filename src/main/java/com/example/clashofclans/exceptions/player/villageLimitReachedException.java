package com.example.clashofclans.exceptions.player;

public class villageLimitReachedException extends RuntimeException {
    public villageLimitReachedException(String message) {
        super(message);
    }
}
