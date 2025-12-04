package com.example.clashofclans.playerRelatedTests;

import com.example.clashofclans.*;
import com.example.clashofclans.buildings.BuildingInstance;
import com.example.clashofclans.enums.*;
import com.example.clashofclans.exceptions.player.villageLimitReachedException;
import com.example.clashofclans.exceptions.village.fullCapacityExeption;
import com.example.clashofclans.exceptions.village.illigalRemoveExeption;
import com.example.clashofclans.exceptions.village.notEnoughResourcesException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestVillage {

    public Village village;

    @Test
    void testVillageConstructorAndGetters() {
        Player owner = new Player("Alice");
        village = new Village(VillageType.regular, owner);

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

    @Test
    void testAddBuildingSuccsessfully() {
        Player p= new Player("Alice");
        Village v2= new Village(VillageType.regular, p);
        BuildingInstance b= new BuildingInstance();

        v2.addBuildingInstance(b);
        assertTrue(v2.getBuildingInstances().contains(b));
    }

    @Test
    void testAddBUnitSuccsessfully() {
        Player p= new Player("Alice");
        Village v2= new Village(VillageType.regular, p);

        Troop b = new Troop(v2, 70, 15, 3,
                AttackDomain.AIR, ResourceKind.DARK_ELIXIR, UnitType.MINION,
                AttackStyle.RANGED_TROOP, 12);

        v2.addUnit(b);
        assertTrue(v2.getUnits().contains((Unit) b));
    }

    @Test
    void testAddBuildingFullCapacityError() {
        Player p= new Player("Alice");
        Village v2= new Village(VillageType.regular, p);

        for(int i=0;i<10;i++){
            v2.addBuildingInstance(new BuildingInstance());
        }
        assertThrows(fullCapacityExeption.class, () -> v2.addBuildingInstance(new BuildingInstance()));
    }

    @Test
    void testAddUnitFullCapacityError() {
        Player p = new Player("Alice");
        Village v2 = new Village(VillageType.regular, p);

        int capacity = v2.getUnitCapacity();

        for (int i = 0; i < capacity; i++) {
            assertDoesNotThrow(() -> v2.addUnit(
                    new Troop(v2, 70, 15, 3,
                            AttackDomain.AIR, ResourceKind.DARK_ELIXIR, UnitType.MINION,
                            AttackStyle.RANGED_TROOP, 12)
            ));
        }

        assertThrows(fullCapacityExeption.class, () -> v2.addUnit(
                new Troop(v2, 70, 15, 3,
                        AttackDomain.AIR, ResourceKind.DARK_ELIXIR, UnitType.MINION,
                        AttackStyle.RANGED_TROOP, 12)
        ));
    }

    @Test
    void testRemoveBuildingSuccsessfully() {
        Player p= new Player("Alice");
        Village v2= new Village(VillageType.regular, p);
        BuildingInstance b= new BuildingInstance();

        v2.addBuildingInstance(b);
        v2.removeBuilding(b);
        assertFalse(v2.getBuildingInstances().contains(b));
    }

    @Test
    void testRemoveBUnitSuccsessfully() {
        Player p= new Player("Alice");
        Village v2= new Village(VillageType.regular, p);

        Troop b = new Troop(village,70, 15, 3,
                AttackDomain.AIR, ResourceKind.DARK_ELIXIR, UnitType.MINION,
                AttackStyle.RANGED_TROOP, 12);

        v2.addUnit(b);
        v2.removeUnit(b);
        assertFalse(v2.getUnits().contains(b));
    }

    @Test
    void testRemoveBuildingNotExistError() {
        Player p= new Player("Alice");
        Village v2= new Village(VillageType.regular, p);

        BuildingInstance b= new BuildingInstance();

        assertThrows(illigalRemoveExeption.class, () -> v2.removeBuilding(b));
    }

    @Test
    void testRemoveUnitNotExistError() {
        Player p= new Player("Alice");
        Village v2= new Village(VillageType.regular, p);

        Troop b=new Troop(village,70, 15, 3,
                AttackDomain.AIR, ResourceKind.DARK_ELIXIR, UnitType.MINION,
                AttackStyle.RANGED_TROOP, 12);
        assertThrows(illigalRemoveExeption.class, () -> v2.removeUnit(b));
    }


}
