package com.example.clashofclans;

import com.example.clashofclans.exceptions.clan.clanBanException;
import com.example.clashofclans.exceptions.clan.clanCreationException;

import java.io.Serializable;
import java.util.ArrayList;

public class Clan implements Serializable {
    private String name;
    private String badge; //also can be enum but idk the values
    private String description;
    private int totalTrophies;
    private String league; //i think this should be enum unless we are treating it like infinate levels
    private ArrayList<Membership> memberships;
    private ArrayList<Player> banList;

    Clan(String name, String description){

        if (name == null || name.trim().isEmpty()) {
            throw new clanCreationException("Clan name cannot be null or empty.");
        }
        if (description == null) {
            throw new clanCreationException("Description cannot be null.");
        }

        this.name = name;
        this.description = description;
        //entry level leauge and badge
        totalTrophies = 0;
        banList = new ArrayList<>();
        this.league = "0";
        this.badge = "0";
    }
    public String getBadge() {
        return badge;
    }

    public String getDescription() {
        return description;
    }

    public int getTotalTrophies() {
        return totalTrophies;
    }

    public String getLeague() {
        return league;
    }

    public String getName() {
        return name;
    }

    public boolean checkIfBanned(Player p) {
        if (p == null) {
            throw new clanBanException("Cannot ban a null player.");
        }
        return banList.contains(p);
    }

    public void addBan(Player p) {
        if (p == null) {
            throw new clanBanException("Cannot ban a null player.");
        }
        if (banList.contains(p)) {
            return;
        }
        banList.add(p);
    }
    //add adjustmennt methods

}
