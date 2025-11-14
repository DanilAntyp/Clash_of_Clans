package com.example.clashofclans;

import enums.AttackDomain;
import enums.AttackStyle;
import enums.ResourceKind;
import enums.UnitType;

import java.util.Objects;

public final class Troop extends Unit {

    public static final boolean availableOncePerPlayer = false;
    private AttackStyle attackStyle;

    private Integer elixirCost;
    private Integer darkElixirCost;

    public Troop(int hitPoint, int damage, int housingSpace,
                 AttackDomain attackDomain, ResourceKind resourceKind, UnitType unitType,
                 AttackStyle attackStyle, Integer cost){
        super(hitPoint,damage,housingSpace,attackDomain,resourceKind,unitType);
        if (!Unit.isTroopType(unitType)) throw new IllegalArgumentException("Troop type is not a troop");
        this.attackStyle = Objects.requireNonNull(attackStyle);
        setCost(resourceKind, cost);
    }

    private void setCost(ResourceKind resourceKind, Integer cost){
        if (cost==null || cost<0) throw new IllegalArgumentException("cost must be >=0");
        if (resourceKind==ResourceKind.ELIXIR){ this.elixirCost=cost; this.darkElixirCost=null; }
        else { this.darkElixirCost=cost; this.elixirCost=null; }
    }

    public AttackStyle getAttackStyle() {
        return attackStyle;
    }

    public void setAttackStyle(AttackStyle attackStyle) {
        if (attackStyle == null) { throw new NullPointerException("attack style cannot be null"); }
        this.attackStyle = attackStyle;
    }

    public Integer getElixirCost() {
        return elixirCost;
    }

    public Integer getDarkElixirCost() {
        return darkElixirCost;
    }
}
