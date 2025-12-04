package com.example.clashofclans;

import com.example.clashofclans.buildings.Building;
import com.example.clashofclans.buildings.BuildingInstance;
import com.example.clashofclans.exceptions.building.InvalidBuildingArgumentException;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class QuantityMaxUnit implements Serializable {
    private int maxValue;

    private List<BuildingInstance>  instances  = new ArrayList<>();

    private static List<QuantityMaxUnit> EXTENT = new ArrayList<>();


    public QuantityMaxUnit(int maxValue) {

        this.maxValue = maxValue;
        EXTENT.add(this);
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
    public static void saveExtent(Path file) {
        ExtentPersistence.saveExtent(EXTENT, file);
    }

    public static void loadExtent(Path file) {
        EXTENT = ExtentPersistence.loadExtent(file);
    }

}
