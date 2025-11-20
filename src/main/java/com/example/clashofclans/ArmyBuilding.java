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

    private List<Unit> campTroops = new ArrayList<>();

    public ArmyBuilding() {}

    public ArmyBuilding(ArmyBuildingType type, int troopsCapacity) {

        if (type == null)
            throw new InvalidBuildingArgumentException("Army building type cannot be null");

        if (troopsCapacity <= 0)
            throw new InvalidBuildingArgumentException("Troop capacity must be > 0");

        this.type = type;
        this.troopsCapacity = troopsCapacity;
    }



    public boolean isEnoughCapacity(long currentTroops) {
        return currentTroops <= troopsCapacity;
    }

    public void addTroop(Unit unit) {
        if (!Unit.isTroopType(unit.getType())){
            throw new UnitCompatibilityException("Troop types do not match, must be Trop type");
        }
        if (unit == null)
            throw new InvalidBuildingArgumentException("Cannot add null unit");

        if (!isEnoughCapacity(getCurrentTroops() + unit.getHousingSpace()))
            throw new InvalidBuildingStateException("Not enough capacity to add troop");

        campTroops.add(unit);
    }


    public long getCurrentTroops() {
        return campTroops.stream().count();
    }


    public ArmyBuildingType getType() { return type; }
    public void setType(ArmyBuildingType type) { this.type = type; }

    public int getTroopsCapacity() { return troopsCapacity; }
    public void setTroopsCapacity(int troopsCapacity) { this.troopsCapacity = troopsCapacity; }
}
