package com.example.clashofclans;

import com.example.clashofclans.enums.SpellType;

import java.io.Serializable;

public class Spell implements Serializable {

    private SpellType type;
    private int cost;
    private double duration;

    public Spell (SpellType type, int cost, double duration) {
        this.type = type;
        this.cost = cost;
        this.duration = duration;
    }

    public SpellType getType() {
        return type;
    }
    public int getCost() {
        return cost;
    }
    public double getDuration() {
        return duration;
    }
}
