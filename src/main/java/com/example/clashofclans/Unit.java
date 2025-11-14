package com.example.clashofclans;


import com.example.clashofclans.enums.AttackDomain;
import com.example.clashofclans.enums.ResourceKind;
import com.example.clashofclans.enums.UnitType;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.*;

public abstract class Unit implements Serializable {

    private int hitPoint;
    private int damage;
    private int housingSpace;

    private final AttackDomain domain;
    private final ResourceKind resourceKind;
    private final UnitType type;

    private static final Set<UnitType> HERO_TYPES = EnumSet.of(UnitType.BARBARIAN_KING, UnitType.ARCHER_QUEEN, UnitType.GRAND_WARDEN);
    private static final Set<UnitType> ELIXIR_USER_TYPES = EnumSet.of(UnitType.BARBARIAN, UnitType.ARCHER, UnitType.GIANT, UnitType.GOBLIN, UnitType.DRAGON, UnitType.BARBARIAN_KING, UnitType.ARCHER_QUEEN, UnitType.GRAND_WARDEN);
    private static final Set<UnitType> DARK_ELIXIR_USER_TYPES = EnumSet.of(UnitType.HOG, UnitType.MINION, UnitType.WITCH, UnitType.VALKYRIE, UnitType.GOLEM, UnitType.MINION_KING);
    private static final Set<UnitType> GROUND_TYPES = EnumSet.of(UnitType.BARBARIAN, UnitType.GIANT, UnitType.ARCHER, UnitType.GOBLIN, UnitType.VALKYRIE, UnitType.HOG, UnitType.WITCH, UnitType.BARBARIAN_KING, UnitType.ARCHER_QUEEN);
    private static final Set<UnitType> AIR_TYPES = EnumSet.of(UnitType.MINION, UnitType.DRAGON, UnitType.GRAND_WARDEN, UnitType.MINION_KING);

    private static final List<Unit> EXTENT = new ArrayList<>();


    protected Unit(int hitPoint,int damage,int housingSpace,
                   AttackDomain attackDomain, ResourceKind resourceKind, UnitType unitType) {

        if (hitPoint<=0) throw new IllegalArgumentException("hitPoint must be > 0");
        if(damage<=0) throw new IllegalArgumentException("damage must be > 0");
        if(housingSpace<=0) throw new IllegalArgumentException("housingSpace must be > 0");
        if (attackDomain == null) throw new NullPointerException("attack domain cannot be null");
        if (resourceKind == null) throw new NullPointerException("resource kind cannot be null");
        if (unitType == null) throw new NullPointerException("unit type cannot be null");
        validateResourceCompatibility(resourceKind, attackDomain, unitType);

        this.hitPoint=hitPoint; this.damage=damage; this.housingSpace=housingSpace;
        this.domain= Objects.requireNonNull(attackDomain);
        this.resourceKind=Objects.requireNonNull(resourceKind);
        this.type=Objects.requireNonNull(unitType);

        EXTENT.add(this);
    }

    private static void validateResourceCompatibility(ResourceKind resourceKind, AttackDomain attackDomain, UnitType unitType){

        if (resourceKind==ResourceKind.ELIXIR && !ELIXIR_USER_TYPES.contains(unitType)
                || resourceKind==ResourceKind.DARK_ELIXIR && !DARK_ELIXIR_USER_TYPES.contains(unitType))
            throw new IllegalArgumentException("type/resourceKind mismatch");

        if (attackDomain==AttackDomain.GROUND && !GROUND_TYPES.contains(unitType)
                || attackDomain==AttackDomain.AIR && !AIR_TYPES.contains(unitType))
            throw new IllegalArgumentException("type/attackDomain mismatch");
    }

    public static List<Unit> getExtent(){ return Collections.unmodifiableList(EXTENT); }

    public static void saveExtent(Path file) throws IOException {
        try (var out = new java.io.ObjectOutputStream(java.nio.file.Files.newOutputStream(file))) {
            out.writeObject(new ArrayList<>(EXTENT));
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadExtent(Path file) throws IOException, ClassNotFoundException {
        EXTENT.clear();
        if (!java.nio.file.Files.exists(file)) return;
        try (var in = new java.io.ObjectInputStream(java.nio.file.Files.newInputStream(file))) {
            var list = (List<Unit>) in.readObject();
            EXTENT.addAll(list);
        }
    }

    public static boolean isHeroType(UnitType t) {
        return HERO_TYPES.contains(t);
    }
    public static boolean isTroopType(UnitType t) {
        return !isHeroType(t);
    }

    public int getRange(){ return domain.range; }


    public int getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(int hitPoint) {
        if (hitPoint<=0) throw new IllegalArgumentException("hitPoint must be > 0");
        this.hitPoint = hitPoint;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        if (damage<=0) throw new IllegalArgumentException("damage must be > 0");
        this.damage = damage;
    }

    public int getHousingSpace() {
        return housingSpace;
    }

    public void setHousingSpace(int housingSpace) {
        if (housingSpace<=0) throw new IllegalArgumentException("housingSpace must be > 0");
        this.housingSpace = housingSpace;
    }

    public AttackDomain getAttackDomain() {
        return domain;
    }

    public ResourceKind getResourceKind() {
        return resourceKind;
    }

    public UnitType getType() {
        return type;
    }
}
