package com.example.clashofclans;

import com.example.clashofclans.enums.VillageType;
import com.example.clashofclans.exceptions.clan.clanBanException;
import com.example.clashofclans.exceptions.player.missingPlayerException;
import com.example.clashofclans.exceptions.player.playerNameException;
import com.example.clashofclans.exceptions.player.villageLimitReachedException;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {

    private String username;
    private int level;
    private int trophies;
    private String leauge; //assuming there are only few types of leauge we might make this one enum
    private Membership membership;
    private ArrayList<Achievement> achivements;
    private ArrayList<Spell> spells;
    private Village[]  villages;

     public Player(String username){
         if (username == null || username.trim().isEmpty()) {
             throw new playerNameException("Username cannot be empty.");
         }
         this.username = username;
        this.level = 1;
        this.trophies = 0;
        this.achivements = new ArrayList<>();
        this.spells=new ArrayList<>();
        this.villages=new Village[2];

        Village village=new Village(VillageType.regular,this);//when new user created they get their village
        this.villages[0]=village;
    }

    public void visitFriendsVillage(Player p){
        if (p == null) {
            throw new missingPlayerException("Cannot ban a null player.");
        }
         //idk the logic
    }

    public void challangeFriend(Player p){
        if (p == null) {
            throw new missingPlayerException("Cannot ban a null player.");
        }
         //idk the logic
    }

    //setter-getters
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new playerNameException("Username cannot be empty.");
        }
         this.username = username;
    }
    public int getLevel() {
        return level;
    }
    public int getTrophies() {
        return trophies;
    }
    public String getLeague() {
        return leauge;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public void setTrophies(int trophies) {
        this.trophies = trophies;
    }
    public void setLeague(String leauge) {
        this.leauge = leauge;
    }
    public int getVillagesCount() {
        int count = 0;
        for (Village v : villages) {
            if (v != null) count++;
        }
        return count;
    }

    public Membership getMembership() {
        return membership;
    }
    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public void addVillageDirectForTest(Village v) {
        if (getVillagesCount() >= 2) {
            throw new villageLimitReachedException("Player cannot have more than two villages.");
        }
         if (villages[0] == null) villages[0] = v;
        else villages[1] = v;
    }
}
//league is gonna be enum
