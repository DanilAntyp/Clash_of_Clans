package com.example.clashofclans;

import com.example.clashofclans.exceptions.building.InvalidBuildingArgumentException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuantityMaxTroops implements Serializable {
    private int maxValue;

    private List<BuildingInstance>  instances  = new ArrayList<>();

    public QuantityMaxTroops() {}

    public QuantityMaxTroops(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getMaxValue() { return maxValue; }
    public void setMaxValue(int maxValue) { this.maxValue = maxValue; }


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
}
