package com.example.clashofclans;

public class Village {

    private final VillageType type;
    private int resources;  //lets say gold is 2x val other is nx or create another type
    //private ArrayList<Buildings> buildings;
    private Player owner;
    //private ArrayList<Unit> units;

    public Village(VillageType type, Player owner) {
        this.type = type;
        resources = 0;
        //buildings=new ArrayList<>();
        //units=new ArrayList<>();
        this.owner = owner;
    }
    public VillageType getType() {
        return type;
    }
    public int getResources() {
        return resources;
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

    public boolean isEnoughResourcesToTrain(int cost) {
        if(resources >= cost) {
            return true;
        }
        return false;
    }

    //add adjustmennt methods

}
