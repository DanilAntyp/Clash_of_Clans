package com.example.clashofclans.theRest;

import com.example.clashofclans.ExtentPersistence;
import com.example.clashofclans.enums.SpellType;
import com.example.clashofclans.exceptions.player.InvalidPlayerForAchievementException;
import com.example.clashofclans.exceptions.player.InvalidPlayerForSpellException;
import com.example.clashofclans.exceptions.unitExceptions.InvalidUnitArgumentException;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Spell implements Serializable {

    private SpellType type;
    private int cost;
    private double duration;
    private Set<Player> players  = new HashSet<>();

    private static List<Spell> EXTENT = new ArrayList<>();

    public Spell (SpellType type, int cost, double duration) {
        this.type = type;
        this.cost = cost;
        this.duration = duration;

        EXTENT.add(this);
    }

    public Set<Player> getPlayers(){return players;}
    public void addPlayer(Player p){
        if (p == null){
            throw new InvalidPlayerForSpellException("Player cannot be null");
        }
        if(players.contains(p)){
            return;
        }
        players.add(p);
        p.addNewSpell(this);
    }
    public void removePlayer(Player p){
        if (p == null){
            throw new InvalidUnitArgumentException("Player cannot be null");
        }
        if(!players.contains(p)){
            throw new InvalidPlayerForSpellException("Player already doesn't have this spell");
        }
        players.remove(p);
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
