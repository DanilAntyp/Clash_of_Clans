package com.example.clashofclans;

import com.example.clashofclans.enums.ResourceKind;
import com.example.clashofclans.enums.VillageType;
import com.example.clashofclans.exceptions.player.villageLimitReachedException;
import com.example.clashofclans.exceptions.village.fullCapacityExeption;
import com.example.clashofclans.exceptions.village.illigalRemoveExeption;
import com.example.clashofclans.exceptions.village.notEnoughResourcesException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;

import static com.example.clashofclans.enums.ResourceKind.GOLD;

public class Village implements Serializable {

    private final VillageType type;
    private EnumMap<ResourceKind, Integer> resources = new EnumMap<>(ResourceKind.class);
    private ArrayList<Building> buildings;
    private final Player owner;
    private ArrayList<Unit> units;
    private int unitCapacity;
    private int buildingCapacity;
    //public Village(Player owner, VillageType type) {}
    //FIXME idk how to represent the interaction between village and clan war


    public Village(VillageType type, Player owner) {
        try {
            if (owner.getVillagesCount() >= 2) {
                throw new villageLimitReachedException("Player cannot have more than 2 villages.");
            }
            this.type = type;
            for (ResourceKind kind : ResourceKind.values()) {
                resources.put(kind, 0);
            }
            buildings=new ArrayList<>();
            units=new ArrayList<>();
            this.owner = owner;
            this.unitCapacity = owner.getVillagesCount();
            this.unitCapacity = 5; //FIXME i need a start value here, does unit capacity grows with level or is it stable (or r there no capacity n we can have unlimited units?)
            this.buildingCapacity = 10; //FIXME same question here

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
    public ArrayList<Building> getBuildings(){
        return buildings;
    }
    public ArrayList<Unit> getUnits(){
        return units;
    }

    public void  addBuilding(Building building){
        try{
            if (buildings.size() < buildingCapacity) {
                buildings.add(building);
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
                throw new illigalRemoveExeption("There is no such unit in this village");
            }
        }catch(illigalRemoveExeption e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public void  removeBuilding(Building building){
        try{
            if(buildings.contains(building)){
                buildings.remove(building);
            }
            else{
                throw new illigalRemoveExeption("There is no such building in this village");
            }
        }catch(illigalRemoveExeption e){
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

    //add adjustmennt methods

}
