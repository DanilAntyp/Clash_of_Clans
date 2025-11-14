package com.example.clashofclans;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClanWar implements Serializable {

    private static final List<ClanWar> extent = new ArrayList<>();

    public static List<ClanWar> getExtent() {
        return Collections.unmodifiableList(extent);
    }

    private static void addToExtent(ClanWar war) {
        if (war == null) throw new IllegalArgumentException("ClanWar cannot be null");
        extent.add(war);
    }

    private Long id;

    private int duration;
    private int reward;
    private int result;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime timestamp;

    public ClanWar(int duration, int reward, int result, LocalDateTime timestamp) {
        setDuration(duration);
        setReward(reward);
        setResult(result);
        setTimestamp(timestamp);

        addToExtent(this);
    }

    public ClanWar() {}


    public void setDuration(int duration) {
        if (duration <= 0)
            throw new IllegalArgumentException("Duration must be positive");
        this.duration = duration;
    }

    public void setReward(int reward) {
        if (reward < 0)
            throw new IllegalArgumentException("Reward must be non-negative");
        this.reward = reward;
    }

    public void setResult(int result) {
        if (result != 0 && result != 1)
            throw new IllegalArgumentException("Result must be 0 or 1");
        this.result = result;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        if (timestamp == null)
            throw new IllegalArgumentException("Timestamp cannot be null");
        this.timestamp = timestamp;
    }

    public int getDuration() {
        return duration;
    }

    public int getReward() {
        return reward;
    }

    public int getResult() {
        return result;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Long getId() {
        return id;
    }

}