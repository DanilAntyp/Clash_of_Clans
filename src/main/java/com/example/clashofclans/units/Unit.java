package com.example.clashofclans.units;


import com.example.clashofclans.ExtentPersistence;
import com.example.clashofclans.theRest.Village;
import com.example.clashofclans.enums.AttackDomain;
import com.example.clashofclans.enums.ResourceKind;
import com.example.clashofclans.enums.UnitType;
import com.example.clashofclans.exceptions.unitExceptions.InvalidUnitArgumentException;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.*;
/*
Composition (Unit - Village):
 * Type: Composition (Whole-Part relationship, 1..1 to 0..*).
 * Implementation: Implemented strictly within the Unit class constructor. A Unit cannot be
 * instantiated without a Village reference (enforced by constructor validation).
 * Reverse Connection: Upon creation, the Unit automatically adds itself to the Village's unit list
 * (village.addUnit(this)).
 * Deletion: The overridden deleteUnit() method in Troop ensures integrity by first removing
 * the unit from any associated BuildingInstance, and then calling the super method to remove
 * the unit from the Village list and destroy the object references.
*/
public abstract class Unit implements Serializable {

    private int hitPoint;
    private int damage;
    private int housingSpace;

    private final AttackDomain domain;
    private final ResourceKind resourceKind;
    private final UnitType type;

    private Village village;

    private static final Set<UnitType> HERO_TYPES = EnumSet.of(UnitType.BARBARIAN_KING, UnitType.ARCHER_QUEEN, UnitType.GRAND_WARDEN);
    private static final Set<UnitType> ELIXIR_USER_TYPES = EnumSet.of(UnitType.BARBARIAN, UnitType.ARCHER, UnitType.GIANT, UnitType.GOBLIN, UnitType.DRAGON, UnitType.BARBARIAN_KING, UnitType.ARCHER_QUEEN, UnitType.GRAND_WARDEN);
    private static final Set<UnitType> DARK_ELIXIR_USER_TYPES = EnumSet.of(UnitType.HOG, UnitType.MINION, UnitType.WITCH, UnitType.VALKYRIE, UnitType.GOLEM, UnitType.MINION_KING);
    private static final Set<UnitType> GROUND_TYPES = EnumSet.of(UnitType.BARBARIAN, UnitType.GIANT, UnitType.ARCHER, UnitType.GOBLIN, UnitType.VALKYRIE, UnitType.HOG, UnitType.WITCH, UnitType.BARBARIAN_KING, UnitType.ARCHER_QUEEN);
    private static final Set<UnitType> AIR_TYPES = EnumSet.of(UnitType.MINION, UnitType.DRAGON, UnitType.GRAND_WARDEN, UnitType.MINION_KING);

    private static List<Unit> EXTENT = new ArrayList<>();


    protected Unit(Village village, int hitPoint,int damage,int housingSpace,
                   AttackDomain attackDomain, ResourceKind resourceKind, UnitType unitType) {

        if(village==null){
            throw new InvalidUnitArgumentException("Unit must belong to a Village");
        }

        if (hitPoint<=0) throw new InvalidUnitArgumentException ("hitPoint must be > 0");
        if(damage<=0) throw new InvalidUnitArgumentException ("damage must be > 0");
        if(housingSpace<=0) throw new InvalidUnitArgumentException("housingSpace must be > 0");
        if (attackDomain == null) throw new InvalidUnitArgumentException("attack domain cannot be null");
        if (resourceKind == null) throw new InvalidUnitArgumentException("resource kind cannot be null");
        if (unitType == null) throw new InvalidUnitArgumentException("unit type cannot be null");
        validateResourceCompatibility(resourceKind, attackDomain, unitType);

        this.village = village;
        this.hitPoint=hitPoint;
        this.damage=damage;
        this.housingSpace=housingSpace;
        this.domain= Objects.requireNonNull(attackDomain);
        this.resourceKind=Objects.requireNonNull(resourceKind);
        this.type=Objects.requireNonNull(unitType);

        this.village.addUnit(this);

        EXTENT.add(this);
    }

    public void deleteUnit(){
        if (this.village!=null){
            this.village.removeUnit(this);
            this.village=null;
        }
        EXTENT.remove(this);
    }

    public Village getVillage() {
        return village;
    }

    private static void validateResourceCompatibility(ResourceKind resourceKind, AttackDomain attackDomain, UnitType unitType){

        if (resourceKind==ResourceKind.ELIXIR && !ELIXIR_USER_TYPES.contains(unitType)
                || resourceKind==ResourceKind.DARK_ELIXIR && !DARK_ELIXIR_USER_TYPES.contains(unitType))
            throw new InvalidUnitArgumentException("type/resourceKind mismatch");

        if (attackDomain==AttackDomain.GROUND && !GROUND_TYPES.contains(unitType)
                || attackDomain==AttackDomain.AIR && !AIR_TYPES.contains(unitType))
            throw new InvalidUnitArgumentException("type/attackDomain mismatch");
    }

    public static List<Unit> getExtent(){ return Collections.unmodifiableList(EXTENT); }

    public static boolean isHeroType(UnitType t) {
        return HERO_TYPES.contains(t);
    }
    public static boolean isTroopType(UnitType t) {
        return !isHeroType(t);
    }

    public int getRange(){ return domain.range; }
    public int getHitPoint() { return hitPoint; }
    public void setHitPoint(int hitPoint) {
        if (hitPoint<=0) throw new InvalidUnitArgumentException("hitPoint must be > 0");
        this.hitPoint = hitPoint;
    }

    public int getDamage() { return damage; }
    public void setDamage(int damage) {
        if (damage<=0) throw new InvalidUnitArgumentException("damage must be > 0");
        this.damage = damage;
    }

    public int getHousingSpace() { return housingSpace; }
    public void setHousingSpace(int housingSpace) {
        if (housingSpace<=0) throw new InvalidUnitArgumentException("housingSpace must be > 0");
        this.housingSpace = housingSpace;
    }

    public AttackDomain getAttackDomain() { return domain; }
    public ResourceKind getResourceKind() { return resourceKind; }
    public UnitType getType() { return type; }

    public static void saveExtent(Path file) { ExtentPersistence.saveExtent(EXTENT, file); }
    public static void loadExtent(Path file) { EXTENT = ExtentPersistence.loadExtent(file); }
}
