package com.example.clashofclans.units;

import com.example.clashofclans.ExtentPersistence;
import com.example.clashofclans.theRest.Village;
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

/*
Association (Troop - BuildingInstance):
 * Type: Basic Association (1 to 0..*). A Troop can reside in one BuildingInstance (Army Camp/Barracks).
 * Implementation: Managed via the setBuildingInstance(BuildingInstance) method in the Troop class.
 * Reverse Connection: The setter handles the bidirectional consistency:
 * 1. It removes the troop from the oldBuildingInstance (checking both the activityQueue and chillBuffer).
 * 2. It adds the troop to the newBuildingInstance's active queue using its public method (addToActivityQueue).
 * Error Handling: The implementation relies on the BuildingInstance's internal logic to throw exceptions
 * if capacities are exceeded, ensuring no invalid states are created during assignment.
 */

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

    @Override
    public void deleteUnit() {
        removeBuildingInstance();
        super.deleteUnit();
        EXTENT.remove(this);
    }

    public BuildingInstance getBuildingInstance() {
        return buildingInstance;
    }

    public void setBuildingInstance(BuildingInstance newBuildingInstance) {
        if(this.buildingInstance == newBuildingInstance) return;

        if (this.buildingInstance != null){
            BuildingInstance oldBuildingInstance = this.buildingInstance;

            if(oldBuildingInstance.getActivityQueue().contains(this)) {
                oldBuildingInstance.removeFromActiveQueue(this);
            }
            else if (oldBuildingInstance.getChillBuffer().contains(this)){
                oldBuildingInstance.getChillBuffer().remove(this);
            }

            this.buildingInstance = null;
        }

        this.buildingInstance = newBuildingInstance;

        if (newBuildingInstance != null){
            boolean alreadyInActive = newBuildingInstance.getActivityQueue().contains(this);
            boolean alreadyInChill = newBuildingInstance.getChillBuffer().contains(this);

            if(!alreadyInActive && !alreadyInChill) {
                newBuildingInstance.addToActivityQueue(this);
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

    public AttackStyle getAttackStyle() { return attackStyle; }
    public void setAttackStyle(AttackStyle attackStyle) {
        if (attackStyle == null) { throw new InvalidUnitArgumentException("attack style cannot be null"); }
        this.attackStyle = attackStyle;
    }
    public Integer getElixirCost() { return elixirCost; }
    public Integer getDarkElixirCost() { return darkElixirCost; }

    public static void saveExtent(Path file) { ExtentPersistence.saveExtent(EXTENT, file); }
    public static void loadExtent(Path file) { EXTENT = ExtentPersistence.loadExtent(file); }
}