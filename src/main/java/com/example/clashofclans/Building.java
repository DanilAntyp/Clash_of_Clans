package com.example.clashofclans;

import java.io.Serializable;

public class Building implements Serializable {
    private double hitPoints;
    private int maxLevel;
    private double buildTime;
    private double resourceCost;
    private double upgradeCost; //derived
    private double upgradeConstructionTime; //derived

    public Building() {}

    //first level building
    public Building(double hitPoints, int maxLevel, double buildTime,
                    double resourceCost) {
        this.hitPoints = hitPoints;
        this.maxLevel = maxLevel;
        this.buildTime = buildTime;
        this.resourceCost = resourceCost;
        this.upgradeCost = 1000;
        this.upgradeConstructionTime = 0.25;
    }

    public BuildingInstance buildNewBuilding(int[] location) {
        System.out.println("Building a new structure...");
        System.out.println("What type of building would you like to build?");

        // create the new building instance
        BuildingInstance newBuilding = new BuildingInstance(
                this,
                this.getHitPoints(),
                1,
                java.time.LocalDateTime.now().plusHours((long) this.getBuildTime()),
                location,
                false
        );

        System.out.println("New building constructed at location: " + location);
        System.out.println("Hit points: " + this.getHitPoints());
        System.out.println("Build time (hours): " + this.getBuildTime());
        System.out.println("Resource cost: " + this.getResourceCost());
        System.out.println("Expected completion time: " + newBuilding.getTimeTillConstruction());

        return newBuilding;
    }


    public double getHitPoints() { return hitPoints; }
    public void setHitPoints(double hitPoints) { this.hitPoints = hitPoints; }

    public int getMaxLevel() { return maxLevel; }
    public void setMaxLevel(int maxLevel) { this.maxLevel = maxLevel; }

    public double getBuildTime() { return buildTime; }
    public void setBuildTime(double buildTime) { this.buildTime = buildTime; }

    public double getResourceCost() { return resourceCost; }
    public void setResourceCost(double resourceCost) { this.resourceCost = resourceCost; }

    //depends on the level of the building
    public void setUpgradeCost(BuildingInstance instance) {
        int currentLevel = instance.getCurrentLevel();
        switch (currentLevel) {
            case 1:
                this.upgradeCost = 1000;
                break;
            case 2:
                this.upgradeCost = 2000;
                break;
            case 3:
                this.upgradeCost = 5000;
                break;
            case 4:
                this.upgradeCost = 20000;
                break;
            case 5:
                this.upgradeCost = 80000;
                break;
            case 6:
                this.upgradeCost = 180000;
                break;
            case 7:
                this.upgradeCost = 360000;
                break;
            case 8:
                this.upgradeCost = 720000;
                break;
            case 9:
                this.upgradeCost = 1200000;
                break;
            default:
                this.upgradeCost = 0;
                System.out.println("Max level reached or invalid level!");
                break;
        }
    }
    //depends on the level of the building
    public void setUpgradeConstructionTime(BuildingInstance instance) {
        int currentLevel = instance.getCurrentLevel();
        switch (currentLevel) {
            case 2:
                this.upgradeConstructionTime = 0.5; // 30 minutes
                break;
            case 3:
                this.upgradeConstructionTime = 1; // 1 hour
                break;
            case 4:
                this.upgradeConstructionTime = 3; // 3 hours
                break;
            case 5:
                this.upgradeConstructionTime = 6; // 6 hours
                break;
            case 6:
                this.upgradeConstructionTime = 12; // 12 hours
                break;
            case 7:
                this.upgradeConstructionTime = 24; // 1 day
                break;
            case 8:
                this.upgradeConstructionTime = 36; // 1.5 days
                break;
            case 9:
                this.upgradeConstructionTime = 48; // 2 days
                break;
            default:
                this.upgradeConstructionTime = 0;
                System.out.println("Max level reached or invalid level!");
                break;
        }
    }

    public double getUpgradeCost() {
        return upgradeCost;
    }

    public double getUpgradeConstructionTime() {
        return upgradeConstructionTime;
    }
}

