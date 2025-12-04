package com.example.clashofclans;

import com.example.clashofclans.enums.ClanRole;
import com.example.clashofclans.exceptions.clan.calnWarAddingExemption;
import com.example.clashofclans.exceptions.clan.clanBanException;
import com.example.clashofclans.exceptions.clan.clanCreationException;
import com.example.clashofclans.exceptions.clan.memberAddingExeption;
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
    private ArrayList<Membership> memberships;
    private ArrayList<Player> banList;
    private ArrayList<ClanWar> clanWars;

    public Clan(String name, String description){

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

    public ArrayList getMemberships() {
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
        removeMembership(p.getMembership());
        banList.add(p);
    }

    public void addMembership(Membership membership){
        try{
            if (membership == null) {
                throw new memberAddingExeption("Cannot ban a null player.");
            }
            else if (banList.contains(membership)) {
                throw new memberAddingExeption("This playyer is banned from the clan");
            }
            else if (memberships.contains(membership)) {
                throw new memberAddingExeption("This playyer is already in the clan");
            }else {
                Membership m=new Membership(ClanRole.MEMBER, LocalDate.now());
                memberships.add(membership);
            }
        }catch(memberAddingExeption e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public void removeMembership(Membership membership){
        try{
            if(memberships.contains(membership)){
                memberships.remove(membership);
            }
            else{
                throw new illigalRemoveExeption("There is no such member in this clan");
            }
        }catch(illigalRemoveExeption e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public void addClanWar(ClanWar clanWar){
        try{
            if (clanWar == null) {
                throw new calnWarAddingExemption("No such clan war exists");
            }
            else if (clanWars.contains(clanWar)) {
                throw new calnWarAddingExemption("Clan war is already in the clan");
            }
            this.clanWars.add(clanWar);

        }catch(calnWarAddingExemption e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    /*public void removeClanWar(ClanWar clanWar){
        try{
            if (clanWar == null) {
                throw new calnWarAddingExemption("No such clan war exists");
            }
            else if (!clanWars.contains(clanWar)) {
                throw new calnWarAddingExemption("Clan war is not in the clan");
            }
            this.clanWars.remove(clanWar);
            clanWar.removeClan(this);

        }catch(calnWarAddingExemption e){
            System.out.println(e.getMessage());
            throw e;
        }
    }*/

    //add adjustmennt methods

}
