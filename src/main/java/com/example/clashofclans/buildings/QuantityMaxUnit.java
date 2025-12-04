package com.example.clashofclans.buildings;

import com.example.clashofclans.ExtentPersistence;
import com.example.clashofclans.exceptions.building.InvalidBuildingArgumentException;
import com.example.clashofclans.exceptions.unitExceptions.InvalidUnitArgumentException;
import com.example.clashofclans.exceptions.unitExceptions.UnitPersistencyException;
import com.example.clashofclans.units.Unit;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class QuantityMaxUnit implements Serializable {
    private int maxValue;

    private List<BuildingInstance>  instances  = new ArrayList<>();
    private List<Unit> units = new ArrayList<>();

    private static List<QuantityMaxUnit> EXTENT = new ArrayList<>();


    public QuantityMaxUnit(int maxValue) {
        this.maxValue = maxValue;
        EXTENT.add(this);
    }

    public int getMaxValue() { return maxValue; }
    public void setMaxValue(int maxValue) { 
        this.maxValue = maxValue; 
    }

    public void addUnit (Unit unit){
        if (unit == null){
            throw new InvalidUnitArgumentException("Unit cannot be null");
        }
        if (units.contains(unit)){
            throw new InvalidUnitArgumentException("Duplicate units");
        }
        units.add(unit);
    }

    public void removeUnit(Unit unit){
        if (unit == null){
            throw new InvalidUnitArgumentException("Unit cannot be null");
        }
        units.remove(unit);
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
    public List<Unit> getUnits() {
        return units;
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
