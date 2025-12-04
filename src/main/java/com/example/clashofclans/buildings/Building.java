package com.example.clashofclans.buildings;

import com.example.clashofclans.ExtentPersistence;
import com.example.clashofclans.QuantityMaxUnit;
import com.example.clashofclans.Village;
import com.example.clashofclans.exceptions.building.InvalidBuildingArgumentException;
import com.example.clashofclans.exceptions.building.InvalidBuildingStateException;


import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Building implements Serializable {
    private double hitPoints;
    private int maxLevel;
    private double buildTime;
    private double resourceCost;
    private double upgradeCost; //derived
    private double upgradeConstructionTime; //derived


    private List<BuildingInstance> instances = new ArrayList<>();

    private static List<Building> EXTENT = new ArrayList<>();

    public static List<Building> getExtent() {
        return Collections.unmodifiableList(EXTENT);
    }

    public Building() {}

    //first level building
    public Building(double hitPoints, int maxLevel, double buildTime, double resourceCost) {

        if (hitPoints <= 0)
            throw new InvalidBuildingArgumentException("hitPoints must be greater than zero");

        if (maxLevel <= 0)
            throw new InvalidBuildingArgumentException("maxLevel must be greater than zero");

        if (buildTime < 0)
            throw new InvalidBuildingArgumentException("buildTime cannot be negative");

        if (resourceCost < 0)
            throw new InvalidBuildingArgumentException("resourceCost cannot be negative");

        this.hitPoints = hitPoints;
        this.maxLevel = maxLevel;
        this.buildTime = buildTime;
        this.resourceCost = resourceCost;
        EXTENT.add(this);

    }

    public BuildingInstance buildNewBuilding(int[] location, Village village) {
        System.out.println("Building a new structure...");
        System.out.println("What type of building would you like to build?");

        // create the new building instance
        BuildingInstance newBuilding = new BuildingInstance(
                village,
                this,
                this.getHitPoints(),
                1,
                java.time.LocalDateTime.now().plusHours((long) this.getBuildTime()),
                location,
                false,
                new QuantityMaxUnit(100)
        );

        System.out.println("New building constructed at location: " + location);
        System.out.println("Hit points: " + this.getHitPoints());
        System.out.println("Build time (hours): " + this.getBuildTime());
        System.out.println("Resource cost: " + this.getResourceCost());
        System.out.println("Expected completion time: " + newBuilding.getTimeTillConstruction());

        return newBuilding;
    }


    public double getHitPoints() { return hitPoints; }
    public void setHitPoints(double hp) {
        if (hp <= 0) throw new InvalidBuildingArgumentException("hitPoints must be > 0");
        this.hitPoints = hp;
    }

    public int getMaxLevel() { return maxLevel; }
    public void setMaxLevel(int maxLevel) {
        if (maxLevel <= 0) throw new InvalidBuildingArgumentException("maxLevel must be > 0");
        this.maxLevel = maxLevel;
    }

    public double getBuildTime() { return buildTime; }
    public void setBuildTime(double time) {
        if (time < 0) throw new InvalidBuildingStateException("buildTime cannot be negative");
        this.buildTime = time;
    }
    public double getResourceCost() { return resourceCost; }
    public void setResourceCost(double resourceCost) {
        if (resourceCost < 0) throw new InvalidBuildingArgumentException("resourceCost cannot be negative");
        this.resourceCost = resourceCost;
    }

    //depends on the level of the building
    public void setUpgradeCost(BuildingInstance instance) {
        if (instance == null) throw new InvalidBuildingArgumentException("instance cannot be null");
        int lvl = instance.getCurrentLevel();
        int currentLevel = instance.getCurrentLevel();
        if (lvl > maxLevel)
            throw new InvalidBuildingStateException("Current level exceeds maxLevel");
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
                throw new InvalidBuildingStateException("Invalid or max level for upgrade cost");
        }
    }
    //depends on the level of the building
    public void setUpgradeConstructionTime(BuildingInstance instance) {
        int currentLevel = instance.getCurrentLevel();
            if (instance == null) throw new InvalidBuildingArgumentException("instance cannot be null");
            int lvl = instance.getCurrentLevel();
            currentLevel ++;
            if (lvl > maxLevel)
                throw new InvalidBuildingStateException("Current level exceeds maxLevel");
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
                throw new InvalidBuildingStateException("Invalid or max level for upgrade time");
        }
    }

    public double getUpgradeCost() {
        return upgradeCost;
    }

    public double getUpgradeConstructionTime() {
        return upgradeConstructionTime;
    }


    public void addInstance(BuildingInstance instance) {
        if (instance == null)
            throw new InvalidBuildingArgumentException("Instance cannot be null");
        if (instances.contains(instance))
            throw new InvalidBuildingArgumentException("Duplicate BuildingInstance");

        instances.add(instance);
    }
    public void removeInstance(BuildingInstance instance) {
        if (instance == null)
            throw new InvalidBuildingArgumentException("Instance cannot be null");
        instances.remove(instance);
    }

    public List<BuildingInstance> getInstances() {
        return instances;
    }
    public static void saveExtent(Path file) {
        ExtentPersistence.saveExtent(EXTENT, file);
    }

    public static void loadExtent(Path file) {
        EXTENT = ExtentPersistence.loadExtent(file);
    }



}

