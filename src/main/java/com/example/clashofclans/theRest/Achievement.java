package com.example.clashofclans.theRest;

import com.example.clashofclans.exceptions.player.InvalidPlayerForAchievementException;
import com.example.clashofclans.exceptions.unitExceptions.InvalidUnitArgumentException;

import java.io.Serializable;
import java.util.Set;

public class Achievement implements Serializable {
    private String name;
    private String description;
    private String type;
    private String reward;
    private Set<Player> players;

    public Achievement(String name, String description, String type, String reward) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.reward = reward;
    }

    public Set<Player> players(){
        return players;
    }
    public void addPlayer(Player p){
        if (p == null){
            throw new InvalidPlayerForAchievementException("Player cannot be null");
        }
        players.add(p);
    }
    public void removePlayer(Player p){
        if (p == null){
            throw new InvalidUnitArgumentException("Player cannot be null");
        }
        players.remove(p);
    }
    public void setPlayers(Set<Player> ps){
        if (ps == null){
            throw new InvalidPlayerForAchievementException("Players cannot be null");
        }
        players = ps;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getType() {
        return type;
    }
    public String getReward() {
        return reward;
    }
}
