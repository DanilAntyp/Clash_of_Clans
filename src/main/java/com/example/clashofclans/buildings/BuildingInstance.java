package com.example.clashofclans.buildings;

import com.example.clashofclans.ExtentPersistence;
import com.example.clashofclans.QuantityMaxUnit;
import com.example.clashofclans.Unit;
import com.example.clashofclans.Village;
import com.example.clashofclans.enums.ArmyBuildingType;
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

public class BuildingInstance implements Serializable {
    private Building building;
    private double currentHp;
    private int currentLevel;
    private LocalDateTime timeTillConstruction; //calculated by current time and build time
    private int[] location;//can be null
    private boolean inBag;
    private QuantityMaxUnit quantityMaxUnit;

    private Village vilage;

    private List<Unit> activityQueue = new ArrayList<>();
    private List<Unit> chillBuffer = new ArrayList<>();

    private static List<BuildingInstance> EXTENT = new ArrayList<>();

    public static List<BuildingInstance> getExtent() {
        return Collections.unmodifiableList(EXTENT);
    }

    public BuildingInstance() {}

    public BuildingInstance(Village vilage, Building b, double currentHp, int currentLevel,
                            LocalDateTime timeTillConstruction,
                            int[] location, boolean inBag, QuantityMaxUnit quantityMaxUnit) {
        this.vilage = vilage;
        if (b == null)
            throw new InvalidBuildingArgumentException("Building must be associated");
        this.building = b;
        b.addInstance(this);

        this.currentHp = currentHp;
        this.currentLevel = currentLevel;
        this.timeTillConstruction = timeTillConstruction;
        this.location = location;
        this.inBag = inBag;
        this.quantityMaxUnit = quantityMaxUnit;

        EXTENT.add(this);
    }

    public BuildingInstance(Village vilage,Building b, double currentHp, int currentLevel,
                            LocalDateTime timeTillConstruction,
                            boolean inBag, QuantityMaxUnit quantityMaxUnit) {
        this.vilage = vilage;
        if (b == null)
            throw new InvalidBuildingArgumentException("Building must be associated");
        this.building = b;
        b.addInstance(this);

        this.currentHp = currentHp;
        this.currentLevel = currentLevel;
        this.timeTillConstruction = timeTillConstruction;
        this.location = null;
        this.inBag = inBag;
        this.quantityMaxUnit = quantityMaxUnit;

        EXTENT.add(this);
    }

    public Building getBuilding() {
        return building;
    }


    public void moveToArmyCamp(Unit unit,  BuildingInstance armyCamp) {

        if (unit == null || armyCamp == null)
                    throw new InvalidBuildingArgumentException("unit and armyCamp must not be null");

        if (!(building instanceof ArmyBuilding)){
            throw new InvalidBuildingArgumentException("Building must be of type ArmyBuilding");
        }
        if (!(armyCamp.getBuilding() instanceof ArmyBuilding)){
            throw new InvalidBuildingArgumentException("Building armycamp must be of type ArmyBuilding");
        }

        if (!Unit.isTroopType(unit.getType())){
            throw new UnitCompatibilityException("Troop types do not match, must be Troop type");
        }


        long current = armyCamp.getActivityQueue().size();

        ArmyBuilding armyBuilding = (ArmyBuilding) armyCamp.getBuilding();
        if (!armyBuilding.isEnoughCapacity(current))
            throw new InvalidBuildingStateException("Army camp is full");

        if (armyBuilding.getType() == ArmyBuildingType.armyCamp) {
            armyCamp.addToActivityQueue(unit);// adding to the queue od the object armycamp (that i provided)
            removeFromActiveQueue(unit); // removing from the queue of the object that i calld the mehtod from
        } else {
            throw new InvalidBuildingArgumentException("It's not an army camp");
        }

    }

    public void moveToBarrack(Unit unit, BuildingInstance barrack) {
        if (!(building instanceof ArmyBuilding)){
            throw new InvalidBuildingArgumentException("Building must be of type ArmyBuilding");
        }
        if (!(barrack.getBuilding() instanceof ArmyBuilding)){
            throw new InvalidBuildingArgumentException("Building barrack must be of type ArmyBuilding");
        }
        if (!Unit.isTroopType(unit.getType()))
            throw new UnitCompatibilityException("Only troop-type units can enter barracks");
        if (unit == null || barrack == null)
            throw new InvalidBuildingArgumentException("unit and barrack cannot be null");

        long current = barrack.getActivityQueue().size();

        ArmyBuilding armyBuilding = (ArmyBuilding) barrack.getBuilding();

        if (!armyBuilding.isEnoughCapacity(current))
            throw new InvalidBuildingStateException("Barrack is full");

        if (armyBuilding.getType() == ArmyBuildingType.barracks) {
            barrack.addToTrainingQueue(unit);
            System.out.println("Troop is training!!");
            removeFromActiveQueue(unit);
        }
        else {
            throw new InvalidBuildingStateException("its not a barrack type that you give");
        }


    }

    public void addToActivityQueue(Unit unit) {
        if (activityQueue.contains(unit))
            throw new InvalidBuildingStateException("Unit already exists in Army Camp");
        activityQueue.add(unit);
        System.out.println("Unit added to Army camp");

    }

    public void addToTrainingQueue(Unit unit) {
        if (!(building instanceof ArmyBuilding )){
           throw new InvalidBuildingArgumentException("Building must be of type ArmyBuilding to call this method");
        }
        ArmyBuilding armyBuilding = (ArmyBuilding) building;
        if (armyBuilding.getType() ==  ArmyBuildingType.armyCamp) {
            throw new InvalidBuildingStateException("You cannot call this method on army camp");
        }
        if (activityQueue.contains(unit))
            throw new InvalidBuildingStateException("Unit already exists in training queue");

        int maxSize = quantityMaxUnit.getMaxValue();

        if (activityQueue.size() >= maxSize) {
            addToChillBuffer(unit);
            System.out.println("Queue full → unit placed in chillBuffer");
            return;
        }
        activityQueue.add(unit);
        System.out.println("Unit added to training queue");
    }

    public void removeFromActiveQueue(Unit unit) {
        if (!activityQueue.contains(unit))
            throw new InvalidBuildingStateException("Unit doesn't exists in active queue");
        activityQueue.remove(unit);
        System.out.println("Unit removed to active queue");
    }
    public void  addToChillBuffer(Unit unit) {
        if (chillBuffer.contains(unit))
            throw new InvalidBuildingStateException("Unit already exists in chill buffer");

        chillBuffer.add(unit);
        System.out.println("Unit added to chill buffer");
    }

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

    public QuantityMaxUnit getQuantityMaxTroops() {
        return quantityMaxUnit;
    }

    public void setQuantityMaxTroops(QuantityMaxUnit q) {
        if (q == null)
            throw new InvalidBuildingArgumentException("QuantityMaxTroops cannot be null");
        this.quantityMaxUnit = q;
    }

    public List<Unit> getActivityQueue() {
        return activityQueue;
    }

    public void setActivityQueue(List<Unit> activityQueue) {
        this.activityQueue = activityQueue;
    }

    public List<Unit> getChillBuffer() {
        return chillBuffer;
    }

    public void setChillBuffer(List<Unit> chillBuffer) {
        this.chillBuffer = chillBuffer;
    }

    public static void saveExtent(Path file) {
        ExtentPersistence.saveExtent(EXTENT, file);
    }

    public static void loadExtent(Path file) {
        EXTENT = ExtentPersistence.loadExtent(file);
    }
}
