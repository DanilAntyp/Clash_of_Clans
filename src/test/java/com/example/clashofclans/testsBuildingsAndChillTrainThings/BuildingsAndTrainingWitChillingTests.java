package com.example.clashofclans.testsBuildingsAndChillTrainThings;

import com.example.clashofclans.buildings.*;
import com.example.clashofclans.enums.*;
import com.example.clashofclans.exceptions.building.InvalidBuildingStateException;
import com.example.clashofclans.exceptions.unitExceptions.UnitCompatibilityException;
import com.example.clashofclans.theRest.Player;
import com.example.clashofclans.theRest.Village;
import com.example.clashofclans.units.Hero;
import com.example.clashofclans.units.Troop;
import com.example.clashofclans.units.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BuildingsAndTrainingWitChillingTests{

    private Village village;
    private ArmyBuilding barracks;
    private ArmyBuilding armyCamp;
    private Building building;
    private BuildingInstance buildingInstance;
    private DefensiveBuilding cannon;
    private ResourceBuilding goldMine;
    private BuildingInstance armyCampInstance;
    private BuildingInstance barracksInstance;
    private Unit unit1;
    private Unit unit2;

    @BeforeEach
    void setUp() {
        village = new Village(VillageType.regular, new Player("testPlayer"));
        barracks = new ArmyBuilding(ArmyBuildingType.barracks, 50);
        armyCamp = new ArmyBuilding(ArmyBuildingType.armyCamp, 100);
        building = new Building(100, 5, 2, 500);
        buildingInstance = new BuildingInstance(village, building, 100, 1,
                LocalDateTime.now().plusHours(2), new int[]{0,0}, false
        );
        cannon = new DefensiveBuilding(DefBuildingType.cannon, 50, 5, DefTargetType.ground);
        goldMine = new ResourceBuilding(ResourceBuildingTypes.goldMine, 500, 50);

        barracksInstance = new BuildingInstance(village,barracks, 100, 1,
                LocalDateTime.now().plusHours(1), false);

        armyCampInstance = new BuildingInstance(village,armyCamp, 100, 1,
                LocalDateTime.now().plusHours(1), false);

        unit1 = new Troop(village,
                100, 20, 5,
                AttackDomain.GROUND, ResourceKind.ELIXIR, UnitType.BARBARIAN,
                AttackStyle.GROUND_TROOP, 50
        );
        unit2 = new Troop(village,
                100, 20, 5,
                AttackDomain.GROUND, ResourceKind.ELIXIR, UnitType.BARBARIAN,
                AttackStyle.GROUND_TROOP, 50
        );

    }

    @Test
    public void testAddInstanceToBuilding() {
        assertEquals(1, building.getInstances().size());
    }

    @Test
    public void testRemoveInstanceFromBuilding() {
        building.removeInstance(buildingInstance);
        assertEquals(0, building.getInstances().size());
    }

    @Test
    void testArmyBuildingCapacity() {
        assertTrue(barracks.isEnoughCapacity(20));
        assertFalse(barracks.isEnoughCapacity(100));
    }


    @Test
    void testGettersAndSetters() {
        barracks.setTroopsCapacity(200);
        assertEquals(200, barracks.getTroopsCapacity());
        barracks.setType(ArmyBuildingType.armyCamp);
        assertEquals(ArmyBuildingType.armyCamp, barracks.getType());
    }

    @Test
    void testBuildNewBuilding() {
        BuildingInstance newBuilding = building.buildNewBuilding(new int[]{1,2}, village);
        assertNotNull(newBuilding);
        assertEquals(building.getHitPoints(), newBuilding.getCurrentHp());
        assertEquals(1, newBuilding.getCurrentLevel());
    }

    @Test
    void testUpgradeCostAndTime() {
        building.setUpgradeCost(buildingInstance);
        building.setUpgradeConstructionTime(buildingInstance);
        assertEquals(1000, buildingInstance.getBuilding().getUpgradeCost(), 0.01); // Original cost
    }

    @Test
    void testUpgradeBuildingInstance() {
        int initialLevel = buildingInstance.getCurrentLevel();
        buildingInstance.upgradeBuilding();
        assertEquals(initialLevel + 1, buildingInstance.getCurrentLevel());
    }

    @Test
    void testDefensiveBuildingProperties() {
        assertEquals(DefBuildingType.cannon, cannon.getType());
        assertEquals(50, cannon.getDamagePerSecond());
        assertEquals(5, cannon.getRange());
        assertEquals(DefTargetType.ground, cannon.getTarget());
        cannon.setDamagePerSecond(100);
        assertEquals(100, cannon.getDamagePerSecond());
    }


    @Test
    void testResourceBuildingProperties() {
        assertEquals(ResourceBuildingTypes.goldMine, goldMine.getType());
        assertEquals(500, goldMine.getMaxStorageCapacity());
        assertEquals(50, goldMine.getProductionRate());
    }

    @Test
    void testCalculateMaxStorageCapacity() {
        BuildingInstance instance = new BuildingInstance(village, goldMine, 100, 3, LocalDateTime.now(), false
        );
        goldMine.calculateMaxStorageCapacity(instance);
        assertEquals(3000, goldMine.getMaxStorageCapacity());
    }
    @Test
    void testAddToTrainingQueueAndChillBuffer() {
        // add unit1 to training queue
        barracksInstance.addToTrainingQueue(unit1);
        assertTrue(barracksInstance.getActivityQueue().contains(unit1));
        assertEquals(1, barracksInstance.getActivityQueue().size());

        // add unit2 → training queue limit is 2, should go to chill buffer
        barracksInstance.addToTrainingQueue(unit2);
        Unit unit3 = new Troop(village,
                100, 20, 5,
                AttackDomain.GROUND, ResourceKind.ELIXIR, UnitType.BARBARIAN,
                AttackStyle.GROUND_TROOP, 50
        );
        barracks.setTroopsCapacity(2);
        barracksInstance.addToTrainingQueue(unit3);
        assertTrue(barracksInstance.getChillBuffer().contains(unit3));
        barracks.setTroopsCapacity(50);
    }

    @Test
    void testRemoveFromActiveQueue() {
        barracksInstance.addToTrainingQueue(unit1);
        barracksInstance.removeFromActiveQueue(unit1);
        assertFalse(barracksInstance.getActivityQueue().contains(unit1));
    }

    @Test
    void testAddToArmyQueue() {
        armyCampInstance.addToActivityQueue(unit1);
        assertTrue(armyCampInstance.getActivityQueue().contains(unit1));
    }

    @Test
    void testMoveToArmyCampSuccess() {
        barracksInstance.addToTrainingQueue(unit1);
        barracksInstance.moveToArmyCamp(unit1, armyCampInstance);

        // Unit removed from barracksInstance training queue
        assertFalse(barracksInstance.getActivityQueue().contains(unit1));
        // Unit added to armyCampInstance queue
        assertTrue(armyCampInstance.getActivityQueue().contains(unit1));
    }

    @Test
    void testMoveToArmyCampFailWrongType() {
        Unit invalidUnit =new Hero(village, 1200, 250, 25,
                AttackDomain.GROUND, ResourceKind.ELIXIR, UnitType.BARBARIAN_KING,
                "Rage", 300, "upgrade");
        assertThrows(UnitCompatibilityException.class,
                () -> barracksInstance.moveToArmyCamp(invalidUnit, armyCampInstance));
    }

    @Test
    void testMoveToBarrackSuccess() {
        armyCampInstance.addToActivityQueue(unit1);
        armyCampInstance.moveToBarrack(unit1, barracksInstance);

        // Unit removed from original queue
        assertFalse(armyCampInstance.getActivityQueue().contains(unit1));
        // Unit added to barracks queue
        assertTrue(barracksInstance.getActivityQueue().contains(unit1));
    }

    @Test
    void testMoveToBarrackFailWrongType() {
        Unit invalidUnit = new Hero(village, 1200, 250, 25,
                AttackDomain.GROUND, ResourceKind.ELIXIR, UnitType.BARBARIAN_KING,
                "Rage", 300, "upgrade");
        assertThrows(UnitCompatibilityException.class,
                () -> barracksInstance.moveToBarrack(invalidUnit, barracksInstance));
    }

    @Test
    void testSettersAndGetters() {
        buildingInstance.setCurrentHp(200);
        assertEquals(200, buildingInstance.getCurrentHp());

        buildingInstance.setCurrentLevel(2);
        assertEquals(2, buildingInstance.getCurrentLevel());

        buildingInstance.setInBag(true);
        assertTrue(buildingInstance.isInBag());

        buildingInstance.setLocation(new int[]{5,5});
        assertArrayEquals(new int[]{5,5}, buildingInstance.getLocation());

    }

    @Test
    void testAddDuplicateUnitToQueueThrows() {
        barracksInstance.addToTrainingQueue(unit1);
        assertThrows(InvalidBuildingStateException.class,
                () -> barracksInstance.addToTrainingQueue(unit1));
    }

    @Test
    void testAddDuplicateUnitToChillBufferThrows() {
        barracksInstance.addToTrainingQueue(unit1);
        barracksInstance.addToChillBuffer(unit1);
        assertThrows(InvalidBuildingStateException.class,
                () -> barracksInstance.addToChillBuffer(unit1));
    }

    @Test
    void testAddToUnits_fromBuildingInstance() {
        ArmyBuilding barracksBuilding = new ArmyBuilding(ArmyBuildingType.barracks, 100);
        BuildingInstance barracksInstance = new BuildingInstance(
                village, barracksBuilding, 100, 1,
                LocalDateTime.now(), false
        );

        barracksInstance.addToTrainingQueue(unit1);

        assertTrue(barracksInstance.getActivityQueue().contains(unit1));
    }

    @Test
    void testRemoveFromUnitsQuantity_fromBuildingInstance() {
        ArmyBuilding barracksBuilding = new ArmyBuilding(ArmyBuildingType.barracks, 2);
        BuildingInstance barracksInstance = new BuildingInstance(
                village, barracksBuilding, 100, 1,
                LocalDateTime.now(), false
        );

        barracksInstance.addToTrainingQueue(unit1);
        barracksInstance.removeFromActiveQueue(unit1);

        assertFalse(barracksInstance.getActivityQueue().contains(unit1));
    }
}
