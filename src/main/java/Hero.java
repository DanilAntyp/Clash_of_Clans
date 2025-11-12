import enums.AttackDomain;
import enums.ResourceKind;
import enums.UnitType;

import java.io.Serial;
import java.util.Objects;

public final class Hero extends Unit {

    @Serial
    private static final long serialVersionUID = 1L;

    public static final boolean availableOncePerPlayer = true;
    private String uniqueAbility;
    private Integer regenerationTime;
    private String upgradeSystem;

    public Hero(int hitPoint, int damage, int housingSpace,
                AttackDomain attackDomain, ResourceKind resourceKind, UnitType unitType,
                String uniqueAbility, Integer regenerationTime, String upgradeSystem){
        super(hitPoint,damage,housingSpace,attackDomain,resourceKind,unitType);
        if (!Unit.isHeroType(unitType)) throw new IllegalArgumentException("Hero type is not a hero");
        if (isBlank(uniqueAbility) || isBlank(upgradeSystem))
            throw new IllegalArgumentException("strings cannot be empty");

        this.uniqueAbility=uniqueAbility.trim();
        this.regenerationTime= Objects.requireNonNull(regenerationTime);
        this.upgradeSystem=upgradeSystem.trim();
    }
    private static boolean isBlank(String s){ return s==null || s.trim().isEmpty(); }

    public String getUniqueAbility() {
        return uniqueAbility;
    }

    public void setUniqueAbility(String uniqueAbility) {
        if (isBlank(uniqueAbility)) throw new IllegalArgumentException("string cannot be empty");
        this.uniqueAbility = uniqueAbility.trim();
    }

    public Integer getRegenerationTime() {
        return regenerationTime;
    }

    public void setRegenerationTime(Integer regenerationTime) {
        if (regenerationTime == null) throw new IllegalArgumentException("regenerationTime cannot be null");
        if (regenerationTime < 0) throw new IllegalArgumentException("regenerationTime must be >= 0");
        this.regenerationTime = regenerationTime;
    }

    public String getUpgradeSystem() {
        return upgradeSystem;
    }

    public void setUpgradeSystem(String upgradeSystem) {
        if (isBlank(upgradeSystem)) throw new IllegalArgumentException("string cannot be empty");
        this.upgradeSystem = upgradeSystem.trim();
    }
}