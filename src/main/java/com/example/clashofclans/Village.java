package com.example.clashofclans;

import com.example.clashofclans.enums.ResourceKind;
import com.example.clashofclans.enums.VillageType;

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


    public Village(VillageType type, Player owner) {
        try {
            if (owner.getVillagesCount() >= 2) {
                throw new IllegalStateException("Player cannot have more than 2 villages.");
            }
            this.type = type;
            for (ResourceKind kind : ResourceKind.values()) {
                resources.put(kind, 0);
            }
            //buildings=new ArrayList<>();
            //units=new ArrayList<>();
            this.owner = owner;

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
    //add new buildings and add units

    public void setMoreResources(int res, ResourceKind kind) {
        resources.put(kind, resources.get(kind) + res);
    }
    public boolean isEnoughResourcesToTrain(int cost) {
        if(resources.get(GOLD) >= cost) {
            return true;
        }
        return false;
    }

    //add adjustmennt methods

}
