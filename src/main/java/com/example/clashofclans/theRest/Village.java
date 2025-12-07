package com.example.clashofclans.theRest;

import com.example.clashofclans.ExtentPersistence;
import com.example.clashofclans.buildings.BuildingInstance;
import com.example.clashofclans.enums.ResourceKind;
import com.example.clashofclans.enums.VillageType;
import com.example.clashofclans.exceptions.player.villageLimitReachedException;
import com.example.clashofclans.exceptions.village.fullCapacityExeption;
import com.example.clashofclans.exceptions.village.IlligalVillageExeption;
import com.example.clashofclans.exceptions.village.notEnoughResourcesException;
import com.example.clashofclans.units.Unit;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

public class Village implements Serializable {

    private final VillageType type;
    private EnumMap<ResourceKind, Integer> resources = new EnumMap<>(ResourceKind.class);

//    private ArrayList<Building> buildings;
    private ArrayList<BuildingInstance> buildingInstances;

    private final Player owner;
    private ArrayList<Unit> units;
    private int unitCapacity;
    private int buildingCapacity;
    //public Village(Player owner, VillageType type) {}
    //FIXME idk how to represent the interaction between village and clan war


    private static List<Village> EXTENT = new ArrayList<>();

    public Village(VillageType type, Player owner) {
        try {
            if (owner.getVillagesCount() >= 2) {
                throw new villageLimitReachedException("Player cannot have more than 2 villages.");
            }
            this.type = type;
            for (ResourceKind kind : ResourceKind.values()) {
                resources.put(kind, 0);
            }
            buildingInstances=new ArrayList<>();
            units=new ArrayList<>();
            this.owner = owner;
            owner.addVillage(this);

            this.unitCapacity = 5; //FIXME i need a start value here, does unit capacity grows with level or is it stable (or r there no capacity n we can have unlimited units?)
            this.buildingCapacity = 10; //FIXME same question here

            EXTENT.add(this);

        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            throw e;
        }

    }

    public VillageType getType() {
        return type;
    }
    public int getResources(ResourceKind kind) {
        return resources.get(kind);
    }
    public Player getOwner() {
        return owner;
    }
    public ArrayList<BuildingInstance> getBuildingInstances(){
        return buildingInstances;
    }
    public ArrayList<Unit> getUnits(){
        return units;
    }

    public void  addBuildingInstance(BuildingInstance buildingInstance){
        try{
            if (buildingInstances.size() < buildingCapacity) {
                buildingInstances.add(buildingInstance);
                buildingInstance.addVillage(this);
            } else {
                throw new fullCapacityExeption("This village cant have more buildings");
            }
        }catch(fullCapacityExeption e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public void  addUnit(Unit unit){
        try{
            if (units.size() < unitCapacity) {
                units.add(unit);
            } else {
                throw new fullCapacityExeption("This village cant have more unit");
            }
        }catch(fullCapacityExeption e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public void  removeUnit(Unit unit){
        try{
            if(units.contains(unit)){
                units.remove(unit);
            }
            else{
                throw new IlligalVillageExeption("There is no such unit in this village");
            }
        }catch(IlligalVillageExeption e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public void  removeBuilding(BuildingInstance buildingInstance){
        try{
            if(this.buildingInstances.contains(buildingInstance)){
                this.buildingInstances.remove(buildingInstance);
                buildingInstance.setInBag(true);
                buildingInstance.removeVillage(this);
            }
            else{
                throw new IlligalVillageExeption("There is no such building in this village");
            }
        }catch(IlligalVillageExeption e){
            System.out.println(e.getMessage());
            throw e;
        }
    }


    public void setMoreResources(int res, ResourceKind kind) {
        resources.put(kind, resources.get(kind) + res);
    }
    public void deductResources(int res, ResourceKind kind) {
        if(resources.get(kind) < res) {
            throw new notEnoughResourcesException("Not enough " + kind + ".");
        }
        resources.put(kind, resources.get(kind) - res);
    }
    public boolean isEnoughResourcesToTrain(int cost, ResourceKind kind) {
        if(resources.get(kind) >= cost) {
            return true;
        }
        throw new notEnoughResourcesException("Not enough " + kind + ".");

    }

    public int getUnitCapacity(){return unitCapacity;}
    public int getBuildingCapacity(){return buildingCapacity;}

    //add adjustmennt methods
    public static void saveExtent(Path file) {
        ExtentPersistence.saveExtent(EXTENT, file);
    }

    public static void loadExtent(Path file) {
        EXTENT = ExtentPersistence.loadExtent(file);
    }

    public void delete(){
        for (BuildingInstance b : new ArrayList<>(buildingInstances)) {
            b.removeVillage(this);
        }
        buildingInstances.clear();

        units.clear(); //idk do we wanna kill em also?

        EXTENT.remove(this);
    }
    public static List<Village> getExtent() {
        return Collections.unmodifiableList(EXTENT);
    }
}
