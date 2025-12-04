package com.example.clashofclans.theRest;

import com.example.clashofclans.ExtentPersistence;
import com.example.clashofclans.enums.SpellType;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Spell implements Serializable {

    private SpellType type;
    private int cost;
    private double duration;

    private static List<Spell> EXTENT = new ArrayList<>();

    public Spell (SpellType type, int cost, double duration) {
        this.type = type;
        this.cost = cost;
        this.duration = duration;

        EXTENT.add(this);
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

    public static void saveExtent(Path file) {
        ExtentPersistence.saveExtent(EXTENT, file);
    }

    public static void loadExtent(Path file) {
        EXTENT = ExtentPersistence.loadExtent(file);
    }
}
