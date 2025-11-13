package com.example.clashofclans;

import java.io.Serializable;
import java.time.LocalDateTime;

public class BuildingInstance implements Serializable {
    private double currentHp;
    private int currentLevel;
    private LocalDateTime timeTillConstruction; //calculated by current time and build time
    private String location;//can be null
    private boolean inBag;

    public BuildingInstance() {}

    public BuildingInstance(double currentHp, int currentLevel,
                            LocalDateTime timeTillConstruction,
                            String location, boolean inBag) {
        this.currentHp = currentHp;
        this.currentLevel = currentLevel;
        this.timeTillConstruction = timeTillConstruction;
        this.location = location;
        this.inBag = inBag;
    }

    public BuildingInstance(double currentHp, int currentLevel,
                            LocalDateTime timeTillConstruction,
                            boolean inBag) {
        this.currentHp = currentHp;
        this.currentLevel = currentLevel;
        this.timeTillConstruction = timeTillConstruction;
        this.location = null;
        this.inBag = inBag;
    }

    public boolean isQueueFull() {
        // Placeholder logic — would normally check training/construction queue
        return false;
    }

    public void moveToArmyCamp() {
        System.out.println("Moving building/unit to army camp...");
    }

    public double getCurrentHp() { return currentHp; }
    public void setCurrentHp(double currentHp) { this.currentHp = currentHp; }

    public int getCurrentLevel() { return currentLevel; }
    public void setCurrentLevel(int currentLevel) { this.currentLevel = currentLevel; }

    public LocalDateTime getTimeTillConstruction() { return timeTillConstruction; }
    public void setTimeTillConstruction(LocalDateTime timeTillConstruction) { this.timeTillConstruction = timeTillConstruction; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public boolean isInBag() { return inBag; }
    public void setInBag(boolean inBag) { this.inBag = inBag; }
}
