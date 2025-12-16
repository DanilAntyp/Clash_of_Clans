package com.example.clashofclans.buildings;

import com.example.clashofclans.enums.DefBuildingType;
import com.example.clashofclans.enums.DefTargetType;

import java.io.Serializable;

public class DefensiveBuilding extends Building implements Serializable {
    private DefBuildingType type;
    private double damagePerSecond;
    private double range;
    private DefTargetType target;


    public DefensiveBuilding(double hitPoints,
                             int maxLevel,
                             double buildTime,
                             double resourceCost,
                             DefBuildingType type,
                             double damagePerSecond,
                             double range,
                             DefTargetType target) {

        super(hitPoints, maxLevel, buildTime, resourceCost);

        this.type = type;
        this.damagePerSecond = damagePerSecond;
        this.range = range;
        this.target = target;
    }


    public DefBuildingType getType() { return type; }
    public void setType(DefBuildingType type) { this.type = type; }

    public double getDamagePerSecond() { return damagePerSecond; }
    public void setDamagePerSecond(double damagePerSecond) { this.damagePerSecond = damagePerSecond; }

    public double getRange() { return range; }
    public void setRange(double range) { this.range = range; }

    public DefTargetType getTarget() { return target; }
    public void setTarget(DefTargetType target) { this.target = target; }
}
