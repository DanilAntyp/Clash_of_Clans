package enums;

public enum AttackDomain { GROUND(50), AIR(100);

    public final int range;

    AttackDomain(int r){
        this.range=r;
    }
}