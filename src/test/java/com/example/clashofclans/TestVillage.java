package com.example.clashofclans;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestVillage {

    @Test
    void testVillageConstructorAndGetters() {
        Player owner = new Player("Alice");
        Village village = new Village(VillageType.regular, owner);

        assertEquals(VillageType.regular, village.getType());
        assertEquals(0, village.getResources(ResourceKind.GOLD));
        assertEquals(0, village.getResources(ResourceKind.ELIXIR));
        assertEquals(0, village.getResources(ResourceKind.DARK_ELIXIR));
        assertEquals(owner, village.getOwner());
    }

    @Test
    void testIsEnoughResourcesToTrain() {
        Player owner = new Player("Bob");
        Village village = new Village(VillageType.regular, owner);

        assertFalse(village.isEnoughResourcesToTrain(10));
    }

    @Test
    void testCannotCreateMoreThanTwoVillages() {
        Player p = new Player("Alice");


        Village v1 = new Village(VillageType.regular, p);
        p.addVillageDirectForTest(v1);

        assertThrows(IllegalStateException.class, () -> {
            new Village(VillageType.regular, p);
        });
    }
}
