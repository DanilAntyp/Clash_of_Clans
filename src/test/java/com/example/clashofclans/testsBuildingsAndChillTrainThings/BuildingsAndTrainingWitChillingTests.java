package com.example.clashofclans.testsBuildingsAndChillTrainThings;

import com.example.clashofclans.*;
import com.example.clashofclans.enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BuildingsAndTrainingWitChillingTests{

    private ArmyBuilding barracks;
    private ArmyBuilding armyCamp;
    private Building building;
    private BuildingInstance buildingInstance;
    private DefensiveBuilding cannon;
    private ResourceBuilding goldMine;

    @BeforeEach
    void setUp() {
        barracks = new ArmyBuilding(ArmyBuildingType.barracks, 50);
        armyCamp = new ArmyBuilding(ArmyBuildingType.armyCamp, 100);
        building = new Building(100, 5, 2, 500);
        buildingInstance = new BuildingInstance(building, 100, 1,
                LocalDateTime.now().plusHours(2), new int[]{0,0}, false);
        cannon = new DefensiveBuilding(DefBuildingType.cannon, 50, 5, DefTargetType.ground);
        goldMine = new ResourceBuilding(ResourceBuildingTypes.goldMine, 500, 50);
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
    void testAddAndGetTroops() {
        Unit testunit =  new Troop(
                100, 20, 5,
                AttackDomain.GROUND, ResourceKind.ELIXIR, UnitType.ARCHER,
                AttackStyle.RANGED_TROOP, 50
        );
        barracks.addTroop(testunit);
        barracks.addTroop(testunit);
        assertEquals(2, barracks.getCurrentTroops());
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
        BuildingInstance newBuilding = building.buildNewBuilding(new int[]{1,2});
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
    void testMoveTroopsToArmyCamp() {
        Unit testunit =  new Troop(
                100, 20, 5,
                AttackDomain.GROUND, ResourceKind.ELIXIR, UnitType.ARCHER,
                AttackStyle.RANGED_TROOP, 50
        );
        barracks.addTroop(testunit);
        buildingInstance.moveToArmyCamp(testunit, armyCamp);
        assertEquals(1, armyCamp.getCurrentTroops());
    }

    @Test
    void testQueueFull() {
        BuildingInstance barracksInstance = new BuildingInstance(barracks, 100, 1, LocalDateTime.now(), false);
        for(int i=0; i<10; i++) {
            Unit testunit =  new Troop(
                    100, 20, 5,
                    AttackDomain.GROUND, ResourceKind.ELIXIR, UnitType.ARCHER,
                    AttackStyle.RANGED_TROOP, 50
            );
            barracksInstance.moveToArmyCamp(testunit, armyCamp);
        }
        assertFalse(barracksInstance.isQueueFull());
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
        BuildingInstance instance = new BuildingInstance(goldMine, 100, 3, LocalDateTime.now(), false);
        goldMine.calculateMaxStorageCapacity(instance);
        assertEquals(3000, goldMine.getMaxStorageCapacity());
    }

}
