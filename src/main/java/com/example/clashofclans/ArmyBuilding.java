package com.example.clashofclans;

import com.example.clashofclans.enums.ArmyBuildingType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ArmyBuilding extends Building implements Serializable {
    private ArmyBuildingType type;
    private int troopsCapacity;

    private List<Unit> campTroops = new ArrayList<>();

    public ArmyBuilding() {}

    public ArmyBuilding(ArmyBuildingType type, int troopsCapacity) {
        this.type = type;
        this.troopsCapacity = troopsCapacity;
    }


    public boolean isEnoughCapacity(long currentTroops) {
        return currentTroops <= troopsCapacity;
    }

    public void addTroop(Unit unit) {
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
