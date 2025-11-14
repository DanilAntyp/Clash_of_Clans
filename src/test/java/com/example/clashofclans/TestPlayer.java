package com.example.clashofclans;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestPlayer {

    @Test
    void testPlayerConstructor() {
        Player player = new Player("Mike");

        assertEquals("Mike", player.getUsername());
        assertEquals(1, player.getLevel());
        assertEquals(0, player.getTrophies());
    }

    @Test
    void testSetters() {
        Player player = new Player("Test");

        player.setLevel(5);
        player.setTrophies(300);

        assertEquals(5, player.getLevel());
        assertEquals(300, player.getTrophies());
    }


}
