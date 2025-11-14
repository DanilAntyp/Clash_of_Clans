package com.example.clashofclans;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestClan {

    @Test
    void testClanConstructorAndGetters() {
        Clan clan = new Clan("Warriors", "A strong clan");

        assertEquals("Warriors", clan.getName());
        assertEquals("A strong clan", clan.getDescription());
        assertEquals(0, clan.getTotalTrophies());
        assertFalse(clan.checkIfBanned(new Player("John"))); // empty banList
    }
}
