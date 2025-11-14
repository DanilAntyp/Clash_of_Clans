package com.example.clashofclans;

import java.util.ArrayList;

public class Clan {
    private String name;
    private String badge; //also can be enum but idk the values
    private String description;
    private int totalTrophies;
    private String league; //i think this should be enum unless we are treating it like infinate levels
    //private ArrayList<Membership> memberships;
    private ArrayList<Player> banList;

    Clan(String name, String description){
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
        return banList.contains(p);
    }

    //add adjustmennt methods

}
