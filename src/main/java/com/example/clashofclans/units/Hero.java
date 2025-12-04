package com.example.clashofclans.units;


import com.example.clashofclans.theRest.Village;
import com.example.clashofclans.enums.AttackDomain;
import com.example.clashofclans.enums.ResourceKind;
import com.example.clashofclans.enums.UnitType;
import com.example.clashofclans.exceptions.unitExceptions.InvalidUnitArgumentException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Hero extends Unit {

    public static  boolean availableOncePerPlayer = true;
    private String uniqueAbility;
    private Integer regenerationTime;
    private String upgradeSystem;

    private static List<Unit> EXTENT = new ArrayList<>();

    public Hero(Village village, int hitPoint, int damage, int housingSpace,
                AttackDomain attackDomain, ResourceKind resourceKind, UnitType unitType,
                String uniqueAbility, Integer regenerationTime, String upgradeSystem){
        super(village, hitPoint,damage,housingSpace,attackDomain,resourceKind,unitType);
        if (!Unit.isHeroType(unitType)) throw new InvalidUnitArgumentException("Hero type is not a hero");
        if (isBlank(uniqueAbility) || isBlank(upgradeSystem))
            throw new InvalidUnitArgumentException("strings cannot be empty");

        this.uniqueAbility=uniqueAbility.trim();
        this.regenerationTime= Objects.requireNonNull(regenerationTime);
        this.upgradeSystem=upgradeSystem.trim();

        EXTENT.add(this);
    }
    private static boolean isBlank(String s){ return s==null || s.trim().isEmpty(); }

    public String getUniqueAbility() {
        return uniqueAbility;
    }

    public void setUniqueAbility(String uniqueAbility) {
        if (isBlank(uniqueAbility)) throw new InvalidUnitArgumentException("string cannot be empty");
        this.uniqueAbility = uniqueAbility.trim();
    }

    public Integer getRegenerationTime() {
        return regenerationTime;
    }

    public void setRegenerationTime(Integer regenerationTime) {
        if (regenerationTime == null) throw new InvalidUnitArgumentException("regenerationTime cannot be null");
        if (regenerationTime < 0) throw new InvalidUnitArgumentException("regenerationTime must be >= 0");
        this.regenerationTime = regenerationTime;
    }

    public String getUpgradeSystem() {
        return upgradeSystem;
    }

    public void setUpgradeSystem(String upgradeSystem) {
        if (isBlank(upgradeSystem)) throw new InvalidUnitArgumentException("string cannot be empty");
        this.upgradeSystem = upgradeSystem.trim();
    }


}
