package com.example.clashofclans;

import com.example.clashofclans.enums.ArmyBuildingType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BuildingInstance implements Serializable {
    private Building buildingType;
    private double currentHp;
    private int currentLevel;
    private LocalDateTime timeTillConstruction; //calculated by current time and build time
    private int[] location;//can be null
    private boolean inBag;

    private List<Integer> trainingQueue = new ArrayList<>();
    private List<Integer> chillBuffer = new ArrayList<>();


    public BuildingInstance() {}

    public BuildingInstance(Building buildingType, double currentHp, int currentLevel,
                            LocalDateTime timeTillConstruction,
                            int[] location, boolean inBag) {
        this.buildingType = buildingType;
        this.currentHp = currentHp;
        this.currentLevel = currentLevel;
        this.timeTillConstruction = timeTillConstruction;
        this.location = location;
        this.inBag = inBag;
    }

    public BuildingInstance(Building buildingType, double currentHp, int currentLevel,
                            LocalDateTime timeTillConstruction,
                            boolean inBag) {
        this.buildingType = buildingType;
        this.currentHp = currentHp;
        this.currentLevel = currentLevel;
        this.timeTillConstruction = timeTillConstruction;
        this.location = null;
        this.inBag = inBag;
    }

    public Building getBuildingType() {
        return buildingType;
    }

    public boolean isQueueFull() {
        if (!(buildingType instanceof ArmyBuilding) ||
                ((ArmyBuilding) buildingType).getType() != ArmyBuildingType.barracks) {
            return false;
        }

        int maxQueueSize = 10;
        boolean full = trainingQueue.size() >= maxQueueSize;

        if (full) System.out.println("Queue full!");
        else System.out.println("Queue has space.");

        return full;
    }


    //later change integer to unit (troop)
    public void moveToArmyCamp(Integer unit, ArmyBuilding armyCamp) {
        if (!(buildingType instanceof ArmyBuilding) ||
                ((ArmyBuilding) buildingType).getType() != ArmyBuildingType.barracks) {
            System.out.println("Only Barracks can move units to Army Camp.");
            return;
        }

        long currentTroopsInCamp = armyCamp.getCurrentTroops(); // need to implement
        if (armyCamp.isEnoughCapacity(currentTroopsInCamp + unit)) {
            armyCamp.addTroop(unit); // need method in ArmyBuilding
            trainingQueue.remove(unit);
            System.out.println("Unit moved successfully to Army Camp!");
        } else {
            chillBuffer.add(unit);
            System.out.println("Army Camp full! Holding unit in chill/ready buffer...");
        }
    }



    //changed - it's not in building anymore
    public void upgradeBuilding() {
        System.out.println("Upgrading building to next level...");
        currentLevel++;
        System.out.println("Current level upgraded to: " + currentLevel);
    }
    public double getCurrentHp() { return currentHp; }
    public void setCurrentHp(double currentHp) { this.currentHp = currentHp; }

    public int getCurrentLevel() { return currentLevel; }
    public void setCurrentLevel(int currentLevel) { this.currentLevel = currentLevel; }

    public LocalDateTime getTimeTillConstruction() { return timeTillConstruction; }
    public void setTimeTillConstruction(LocalDateTime timeTillConstruction) { this.timeTillConstruction = timeTillConstruction; }

    public int[] getLocation() { return location; }
    public void setLocation(int[] location) { this.location = location; }

    public boolean isInBag() { return inBag; }
    public void setInBag(boolean inBag) { this.inBag = inBag; }
}
