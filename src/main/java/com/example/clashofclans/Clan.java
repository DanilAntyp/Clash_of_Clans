package com.example.clashofclans;

import com.example.clashofclans.enums.ClanRole;
import com.example.clashofclans.exceptions.clan.clanBanException;
import com.example.clashofclans.exceptions.clan.clanCreationException;
import com.example.clashofclans.exceptions.clan.memberAddingExeption;
import com.example.clashofclans.exceptions.village.fullCapacityExeption;
import com.example.clashofclans.exceptions.village.illigalRemoveExeption;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Clan implements Serializable {
    private String name;
    private String badge;
    private String description;
    private int totalTrophies;
    private String league;
    private Map<Player ,Membership> memberships;
    private ArrayList<Player> banList;
    //TODO idk how to connect this one with clan wars

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
        this.memberships = new HashMap<>();
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

    public Map getMemberships() {
        return memberships;
    }

    public String getName() {
        return name;
    }

    public ArrayList getBanList() {
        return banList;
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
        removeMember(p);
        banList.add(p);
    }

    public void  addMember(Player player){
        try{
            if (player == null) {
                throw new memberAddingExeption("Cannot ban a null player.");
            }
            else if (banList.contains(player)) {
                throw new memberAddingExeption("This playyer is banned from the clan");
            }
            else if (memberships.containsKey(player)) {
                throw new memberAddingExeption("This playyer is already in the clan");
            }else {
                Membership m=new Membership(ClanRole.MEMBER, LocalDate.now());
                memberships.put(player,m);
            }
        }catch(memberAddingExeption e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public void  removeMember(Player player){
        try{
            if(memberships.containsKey(player)){
                memberships.remove(player);
            }
            else{
                throw new illigalRemoveExeption("There is no such member in this clan");
            }
        }catch(illigalRemoveExeption e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    //add adjustmennt methods

}
