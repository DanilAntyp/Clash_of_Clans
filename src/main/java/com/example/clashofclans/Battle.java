package com.example.clashofclans;
import com.example.clashofclans.enums.BattleType;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Battle implements Serializable {

    private static final List<Battle> extent = new ArrayList<>();

    public static List<Battle> getExtent() {
        return Collections.unmodifiableList(extent);
    }

    private static void addToExtent(Battle battle) {
        if (battle == null) throw new IllegalArgumentException("Battle cannot be null");
        extent.add(battle);
    }

    private Long id;

    private BattleType type;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime time;

    private int stars;
    private int loot;

    public Battle(BattleType type, LocalDateTime time, int stars, int loot) {
        setType(type);
        setTime(time);
        setStars(stars);
        setLoot(loot);

        addToExtent(this);
    }

    public Battle() { }


    public void setType(BattleType type) {
        if (type == null)
            throw new IllegalArgumentException("type cannot be null");
        this.type = type;
    }

    public void setTime(LocalDateTime time) {
        if (time == null)
            throw new IllegalArgumentException("Battle time cannot be null");
        this.time = time;
    }

    public void setStars(int stars) {
        if (stars < 0 || stars > 3)
            throw new IllegalArgumentException("Stars must be between 0 and 3");
        this.stars = stars;
    }

    public void setLoot(int loot) {
        if (loot < 0)
            throw new IllegalArgumentException("Loot must be 0 or positive");
        this.loot = loot;
    }

    public int computeResults() {
        return stars * 100 + loot;
    }

    public int getLoot() {
        return loot;
    }

    public int getStars() {
        return stars;
    }

    public Long getId() {
        return id;
    }

    public BattleType getType() {
        return type;
    }

    public LocalDateTime getTime (){
        return time;
    }
}