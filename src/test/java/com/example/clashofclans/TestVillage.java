package com.example.clashofclans;

import com.example.clashofclans.enums.ResourceKind;
import com.example.clashofclans.enums.VillageType;
import com.example.clashofclans.exceptions.player.villageLimitReachedException;
import com.example.clashofclans.exceptions.village.notEnoughResourcesException;
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

        assertThrows(notEnoughResourcesException.class, () -> {
            village.isEnoughResourcesToTrain(10, ResourceKind.GOLD);
        });
    }

    @Test
    void testCannotCreateMoreThanTwoVillages() {
        Player p = new Player("Alice");

        Village v2 = new Village(VillageType.regular, p);
        p.addVillageDirectForTest(v2);

        assertThrows(villageLimitReachedException.class, () -> {
            new Village(VillageType.regular, p);
        });
    }

}
