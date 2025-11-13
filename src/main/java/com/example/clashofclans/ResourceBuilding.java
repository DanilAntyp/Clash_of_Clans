package com.example.clashofclans;

import com.example.clashofclans.enums.ResourceBuildingTypes;

import java.io.Serializable;

public class ResourceBuilding extends Building implements Serializable {
    private ResourceBuildingTypes type;
    private double maxStorageCapacity; // derived
    private double productionRate; //expressed in resource per hour

    public ResourceBuilding() {}

    public ResourceBuilding(ResourceBuildingTypes type, double maxStorageCapacity, double productionRate) {
        this.type = type;
        this.maxStorageCapacity = maxStorageCapacity;
        this.productionRate = productionRate;
    }

    public void addResources() {
        System.out.println("Resources added to storage.");
    }


    public ResourceBuildingTypes getType() { return type; }
    public void setType(ResourceBuildingTypes type) { this.type = type; }

    public double getMaxStorageCapacity() { return maxStorageCapacity; }
    public void setMaxStorageCapacity(double maxStorageCapacity) { this.maxStorageCapacity = maxStorageCapacity; }

    public double getProductionRate() { return productionRate; }
    public void setProductionRate(double productionRate) { this.productionRate = productionRate; }

    public void calculateMaxStorageCapacity(BuildingInstance instance) {
        int currentLevel = instance.getCurrentLevel();

        switch (currentLevel) {
            case 1:
                this.maxStorageCapacity = 500;
                break;
            case 2:
                this.maxStorageCapacity = 1000;
                break;
            case 3:
                this.maxStorageCapacity = 3000;
                break;
            case 4:
                this.maxStorageCapacity = 6000;
                break;
            case 5:
                this.maxStorageCapacity = 12000;
                break;
            case 6:
                this.maxStorageCapacity = 25000;
                break;
            case 7:
                this.maxStorageCapacity = 50000;
                break;
            case 8:
                this.maxStorageCapacity = 100000;
                break;
            case 9:
                this.maxStorageCapacity = 200000;
                break;
            case 10:
                this.maxStorageCapacity = 300000;
                break;
            default:
                this.maxStorageCapacity = 0;
                System.out.println("Invalid or maximum level reached for storage building.");
                break;
        }
    }
}
