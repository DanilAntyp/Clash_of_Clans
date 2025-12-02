package com.example.clashofclans;

import com.example.clashofclans.exceptions.building.BuildingLevelException;
import com.example.clashofclans.exceptions.building.InvalidBuildingArgumentException;
import com.example.clashofclans.exceptions.building.InvalidBuildingStateException;
import com.example.clashofclans.exceptions.unitExceptions.UnitCompatibilityException;

import java.io.Serializable;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.clashofclans.enums.ArmyBuildingType.barracks;

public class BuildingInstance implements Serializable {
    private Building building;
    private double currentHp;
    private int currentLevel;
    private LocalDateTime timeTillConstruction; //calculated by current time and build time
    private int[] location;//can be null
    private boolean inBag;

    private List<Unit> trainingQueue = new ArrayList<>();
    private List<Unit> chillBuffer = new ArrayList<>();

    private static List<BuildingInstance> EXTENT = new ArrayList<>();

    public static List<BuildingInstance> getExtent() {
        return Collections.unmodifiableList(EXTENT);
    }

    public BuildingInstance() {}

    public BuildingInstance(Building b, double currentHp, int currentLevel,
                            LocalDateTime timeTillConstruction,
                            int[] location, boolean inBag) {
        if (b == null)
            throw new InvalidBuildingArgumentException("Building must be associated");
        this.building = b;
        b.addInstance(this);
        this.currentHp = currentHp;
        this.currentLevel = currentLevel;
        this.timeTillConstruction = timeTillConstruction;
        this.location = location;
        this.inBag = inBag;
        EXTENT.add(this);
    }

    public BuildingInstance(Building building, double currentHp, int currentLevel,
                            LocalDateTime timeTillConstruction,
                            boolean inBag) {
        this.building = building;
        this.currentHp = currentHp;
        this.currentLevel = currentLevel;
        this.timeTillConstruction = timeTillConstruction;
        this.location = null;
        this.inBag = inBag;
    }

    public Building getBuilding() {
        return building;
    }

    public boolean isQueueFull() {
        if (!(building instanceof ArmyBuilding) ||
                ((ArmyBuilding) building).getType() != barracks) {
            return false;
        }

        int maxQueueSize = 10;
        boolean full = trainingQueue.size() >= maxQueueSize;

        if (full) System.out.println("Queue full!");
        else System.out.println("Queue has space.");

        return full;
    }


    //later change integer to unit (troop)
    public void moveToArmyCamp(Unit unit, ArmyBuilding armyCamp) {
        if (!Unit.isTroopType(unit.getType())){
            throw new UnitCompatibilityException("Troop types do not match, must be Trop type");
        }
        if (unit == null || armyCamp == null)
            throw new InvalidBuildingArgumentException("unit and armyCamp must not be null");

        long current = armyCamp.getCurrentTroops();

        if (!armyCamp.isEnoughCapacity(current + unit.getHousingSpace()))
            throw new InvalidBuildingStateException("Army camp is full");

        armyCamp.addTroop(unit);
        trainingQueue.remove(unit);
    }

    public void moveToBarrack(Unit unit, ArmyBuilding barack) {}


    //changed - it's not in building anymore
    public void upgradeBuilding() {
        System.out.println("Upgrading building to next level...");
        currentLevel++;
        System.out.println("Current level upgraded to: " + currentLevel);
    }
    public double getCurrentHp() { return currentHp; }
    public void setCurrentHp(double hp) {
        if (hp <= 0)
            throw new InvalidBuildingArgumentException("HP must be greater than zero");

        this.currentHp = hp;
    }


    public int getCurrentLevel() { return currentLevel; }
    public void setCurrentLevel(int lvl) {

        if (lvl <= 0)
            throw new InvalidBuildingArgumentException("Level must be positive");

        if (lvl > building.getMaxLevel())
            throw new BuildingLevelException("Level exceeds building max level");

        this.currentLevel = lvl;
    }

    public LocalDateTime getTimeTillConstruction() { return timeTillConstruction; }
    public void setTimeTillConstruction(LocalDateTime timeTillConstruction) { this.timeTillConstruction = timeTillConstruction; }

    public int[] getLocation() { return location; }
    public void setLocation(int[] location) { this.location = location; }

    public boolean isInBag() { return inBag; }
    public void setInBag(boolean inBag) { this.inBag = inBag; }

    public static void saveExtent(Path file) {
        ExtentPersistence.saveExtent(EXTENT, file);
    }

    public static void loadExtent(Path file) {
        EXTENT = ExtentPersistence.loadExtent(file);
    }
}
