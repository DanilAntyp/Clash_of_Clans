package com.example.clashofclans;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestVillage {

    @Test
    void testVillageConstructorAndGetters() {
        Player owner = new Player("Alice");
        Village village = new Village(VillageType.regular, owner);

        assertEquals(VillageType.regular, village.getType());
        assertEquals(0, village.getResources());
        assertEquals(owner, village.getOwner());
    }

    @Test
    void testIsEnoughResourcesToTrain() {
        Player owner = new Player("Bob");
        Village village = new Village(VillageType.regular, owner);

        assertFalse(village.isEnoughResourcesToTrain(10));
    }
}
