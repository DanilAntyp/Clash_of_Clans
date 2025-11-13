package com.example.clashofclans;

import com.example.clashofclans.enums.ArmyBuildingType;

import java.io.Serializable;

public class ArmyBuilding extends Building implements Serializable {
    private ArmyBuildingType type;
    private int troopsCapacity;

    public ArmyBuilding() {}

    public ArmyBuilding(ArmyBuildingType type, int troopsCapacity) {
        this.type = type;
        this.troopsCapacity = troopsCapacity;
    }


    public boolean isEnoughCapacity(int currentTroops) {
        return currentTroops <= troopsCapacity;
    }


    public ArmyBuildingType getType() { return type; }
    public void setType(ArmyBuildingType type) { this.type = type; }

    public int getTroopsCapacity() { return troopsCapacity; }
    public void setTroopsCapacity(int troopsCapacity) { this.troopsCapacity = troopsCapacity; }
}
