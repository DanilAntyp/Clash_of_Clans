package com.example.clashofclans;

import com.example.clashofclans.enums.ArmyBuildingType;
import com.example.clashofclans.exceptions.building.InvalidBuildingArgumentException;
import com.example.clashofclans.exceptions.building.InvalidBuildingStateException;
import com.example.clashofclans.exceptions.unitExceptions.UnitCompatibilityException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ArmyBuilding extends Building implements Serializable {
    private ArmyBuildingType type;
    private int troopsCapacity;

    private static List<Building> EXTENT = new ArrayList<>();

    public ArmyBuilding() {}

    public ArmyBuilding(ArmyBuildingType type, int troopsCapacity) {

        if (type == null)
            throw new InvalidBuildingArgumentException("Army building type cannot be null");

        if (troopsCapacity <= 0)
            throw new InvalidBuildingArgumentException("Troop capacity must be > 0");

        this.type = type;
        this.troopsCapacity = troopsCapacity;
        EXTENT.add(this);
    }



    public boolean isEnoughCapacity(long currentTroops) {
        return currentTroops <= troopsCapacity;
    }


    public ArmyBuildingType getType() { return type; }
    public void setType(ArmyBuildingType type) { this.type = type; }

    public int getTroopsCapacity() { return troopsCapacity; }
    public void setTroopsCapacity(int troopsCapacity) { this.troopsCapacity = troopsCapacity; }
}
