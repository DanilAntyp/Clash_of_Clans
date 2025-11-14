package com.example.clashofclans;

import java.util.EnumMap;

import static com.example.clashofclans.ResourceKind.GOLD;

public class Village {

    private final VillageType type;
    private EnumMap<ResourceKind, Integer> resources = new EnumMap<>(ResourceKind.class);
    //private ArrayList<Buildings> buildings;
    private final Player owner;
    //private ArrayList<Unit> units;


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
    /*public ArrayList<Buildings> getBuildings(){
        return buildings;
    }*/

    /*public ArrayList<Unit> getUnits(){
        return units;
    }*/
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
