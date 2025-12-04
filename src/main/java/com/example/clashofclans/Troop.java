package com.example.clashofclans;


import com.example.clashofclans.buildings.Building;
import com.example.clashofclans.buildings.BuildingInstance;
import com.example.clashofclans.enums.AttackDomain;
import com.example.clashofclans.enums.AttackStyle;
import com.example.clashofclans.enums.ResourceKind;
import com.example.clashofclans.enums.UnitType;
import com.example.clashofclans.exceptions.unitExceptions.InvalidUnitArgumentException;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Troop extends Unit {

    public static final boolean availableOncePerPlayer = false;
    private AttackStyle attackStyle;
    private Integer elixirCost;
    private Integer darkElixirCost;

    private BuildingInstance buildingInstance;

    private static List<Unit> EXTENT = new ArrayList<>();

    public Troop(Village village, int hitPoint, int damage, int housingSpace,
                 AttackDomain attackDomain, ResourceKind resourceKind, UnitType unitType,
                 AttackStyle attackStyle, Integer cost){
        super(village, hitPoint,damage,housingSpace,attackDomain,resourceKind,unitType);
        if (!Unit.isTroopType(unitType)) throw new InvalidUnitArgumentException("Troop type is not a troop");
        this.attackStyle = Objects.requireNonNull(attackStyle);
        setCost(resourceKind, cost);

        EXTENT.add(this);
    }

    public BuildingInstance getBuildingInstance() {
        return buildingInstance;
    }

    public void setBuildingInstance(BuildingInstance newBuildingInstance) {
        if(this.buildingInstance==newBuildingInstance) return;

        if (this.buildingInstance!=null){
            BuildingInstance oldBuildingInstance = this.buildingInstance;

            if(oldBuildingInstance.getActivityQueue().contains(this)) {
                oldBuildingInstance.removeFromActiveQueue(this);
            } else if (oldBuildingInstance.getChillBuffer().remove(this)){
                oldBuildingInstance.getChillBuffer().remove(this);
            }
            //TODO: add method removeFromChillBuffer to BuildingInstance and use it here
            this.buildingInstance = null;
        }

        this.buildingInstance = newBuildingInstance;

        if (newBuildingInstance!=null){
            boolean alreadyInActive = newBuildingInstance.getActivityQueue().contains(this);
            boolean alreadyInChill = newBuildingInstance.getChillBuffer().contains(this);

            if(!alreadyInActive && !alreadyInChill) {
                try{
                    newBuildingInstance.addToActivityQueue(this);
                } catch (Exception ignored){}
            }
        }

    }

    public void removeBuildingInstance() {
        setBuildingInstance(null);
    }

    private void setCost(ResourceKind resourceKind, Integer cost){
        if (cost==null || cost<0) throw new InvalidUnitArgumentException("cost must be >=0");
        if (resourceKind==ResourceKind.ELIXIR){ this.elixirCost=cost; this.darkElixirCost=null; }
        else { this.darkElixirCost=cost; this.elixirCost=null; }
    }

    public AttackStyle getAttackStyle() {
        return attackStyle;
    }

    public void setAttackStyle(AttackStyle attackStyle) {
        if (attackStyle == null) { throw new InvalidUnitArgumentException("attack style cannot be null"); }
        this.attackStyle = attackStyle;
    }

    public Integer getElixirCost() {
        return elixirCost;
    }

    public Integer getDarkElixirCost() {
        return darkElixirCost;
    }

    public static void saveExtent(Path file) {
        ExtentPersistence.saveExtent(EXTENT, file);
    }

    public static void loadExtent(Path file) {
        EXTENT = ExtentPersistence.loadExtent(file);
    }
}
