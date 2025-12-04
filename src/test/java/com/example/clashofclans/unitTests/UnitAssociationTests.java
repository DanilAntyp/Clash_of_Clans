package com.example.clashofclans.unitTests;

import com.example.clashofclans.buildings.ArmyBuilding;
import com.example.clashofclans.buildings.BuildingInstance;
import com.example.clashofclans.buildings.QuantityMaxUnit;
import com.example.clashofclans.enums.*;
import com.example.clashofclans.exceptions.unitExceptions.InvalidUnitArgumentException;
import com.example.clashofclans.theRest.Player;
import com.example.clashofclans.theRest.Village;
import com.example.clashofclans.units.Hero;
import com.example.clashofclans.units.Troop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UnitAssociationTests {

    private Village village;
    private BuildingInstance campInstance1;
    private BuildingInstance campInstance2;

    @BeforeEach
    void setUp() {
        Player player = new Player("TestPlayer");

        village = new Village(VillageType.regular, player);

        ArmyBuilding armyCampType = new ArmyBuilding(ArmyBuildingType.armyCamp, 20);

        QuantityMaxUnit qty = new QuantityMaxUnit(10);

        campInstance1 = new BuildingInstance(village, armyCampType, 1000, 1, LocalDateTime.now(), false, qty);
        campInstance1.setActivityQueue(new ArrayList<>());
        campInstance1.setChillBuffer(new ArrayList<>());

        campInstance2 = new BuildingInstance(village, armyCampType, 1000, 1, LocalDateTime.now(), false, qty);
        campInstance2.setActivityQueue(new ArrayList<>());
        campInstance2.setChillBuffer(new ArrayList<>());
    }


    @Test
    void testComposition_UnitCannotExistWithoutVillage() {
        assertThrows(InvalidUnitArgumentException.class, () -> {
            new Troop(null, 100, 10, 1, AttackDomain.GROUND, ResourceKind.ELIXIR,
                    UnitType.BARBARIAN, AttackStyle.GROUND_TROOP, 100);
            });
    }

    @Test
    void testComposition_UnitAddsSelfToVillage() {
        Troop barb = new Troop(village, 100, 10, 1, AttackDomain.GROUND, ResourceKind.ELIXIR,
                UnitType.BARBARIAN, AttackStyle.GROUND_TROOP, 100);

        assertTrue(village.getUnits().contains(barb));
    }

    @Test
    void testComposition_DeleteUnitRemovesFromVillage() {
        Troop barb = new Troop(village, 100, 10, 1, AttackDomain.GROUND, ResourceKind.ELIXIR,
                UnitType.BARBARIAN, AttackStyle.GROUND_TROOP, 100);


        barb.deleteUnit();


        assertFalse(village.getUnits().contains(barb));
        assertNull(barb.getVillage(), "Unit's reference to Village should be null");
    }


    @Test
    void testAssociation_SetHousingInstance_AddsToActiveQueue() {
        Troop barb = new Troop(village, 100, 10, 1, AttackDomain.GROUND, ResourceKind.ELIXIR,
                UnitType.BARBARIAN, AttackStyle.GROUND_TROOP, 100);

        barb.setBuildingInstance(campInstance1);

        assertEquals(campInstance1, barb.getBuildingInstance());

        assertTrue(campInstance1.getActivityQueue().contains(barb));
    }

    @Test
    void testAssociation_MoveBetweenBuildings() {
        Troop barb = new Troop(village, 100, 10, 1, AttackDomain.GROUND, ResourceKind.ELIXIR,
                UnitType.BARBARIAN, AttackStyle.GROUND_TROOP, 100);

        barb.setBuildingInstance(campInstance1);
        assertTrue(campInstance1.getActivityQueue().contains(barb));


        barb.setBuildingInstance(campInstance2);

        assertFalse(campInstance1.getActivityQueue().contains(barb), "Troop should be removed from old building");

        assertTrue(campInstance2.getActivityQueue().contains(barb), "Troop should be added to new building");
        assertEquals(campInstance2, barb.getBuildingInstance());
    }

    @Test
    void testAssociation_RemoveHousingInstance() {
        Troop barb = new Troop(village, 100, 10, 1, AttackDomain.GROUND, ResourceKind.ELIXIR,
                UnitType.BARBARIAN, AttackStyle.GROUND_TROOP, 100);

        barb.setBuildingInstance(campInstance1);
        barb.setBuildingInstance(null);

        assertNull(barb.getBuildingInstance());
        assertFalse(campInstance1.getActivityQueue().contains(barb),
                "Troop should be removed from building queue upon setting null");
    }

    @Test
    void testAssociation_RemoveFromChillBuffer() {
        Troop barb = new Troop(village, 100, 10, 1, AttackDomain.GROUND, ResourceKind.ELIXIR,
                UnitType.BARBARIAN, AttackStyle.GROUND_TROOP, 100);
        campInstance1.getChillBuffer().add(barb);
        try {
            java.lang.reflect.Field field = Troop.class.getDeclaredField("buildingInstance");
            field.setAccessible(true);
            field.set(barb, campInstance1);
        } catch (Exception e) {
            fail("Reflection failed setup");
        }

        barb.setBuildingInstance(campInstance2);

        assertFalse(campInstance1.getChillBuffer().contains(barb));

        assertTrue(campInstance2.getActivityQueue().contains(barb),
                "Troop should move to active queue of new building");
    }


    @Test
    void testHeroComposition() {
        assertThrows(InvalidUnitArgumentException.class, () -> {
            new Hero(null, 500, 50, 25, AttackDomain.GROUND, ResourceKind.ELIXIR,
                    UnitType.BARBARIAN_KING, "Iron Fist", 60, "Levels");
        });

        Hero king = new Hero(village, 500, 50, 25, AttackDomain.GROUND, ResourceKind.ELIXIR,
                UnitType.BARBARIAN_KING, "Iron Fist", 60, "Levels");

        assertTrue(village.getUnits().contains(king), "Hero should be added to Village units");
    }
}