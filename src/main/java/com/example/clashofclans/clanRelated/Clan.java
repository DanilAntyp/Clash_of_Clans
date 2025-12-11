package com.example.clashofclans.clanRelated;

import com.example.clashofclans.ExtentPersistence;
import com.example.clashofclans.theRest.Player;
import com.example.clashofclans.enums.ClanRole;
import com.example.clashofclans.exceptions.clan.calnWarAddingExemption;
import com.example.clashofclans.exceptions.clan.clanBanException;
import com.example.clashofclans.exceptions.clan.clanCreationException;
import com.example.clashofclans.exceptions.clan.memberAddingExeption;
import com.example.clashofclans.exceptions.village.IlligalVillageExeption;

import java.io.Serializable;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Clan implements Serializable {
    private String name;
    private String badge;
    private String description;
    private int totalTrophies;
    private String league;
    private ArrayList<Membership> memberships;
    private ArrayList<Player> banList;
    private ArrayList<ClanWar> clanWars;

    private static List<Clan> EXTENT = new ArrayList<>();

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
        this.memberships=new ArrayList<>();
        this.clanWars=new ArrayList<>();

        EXTENT.add(this);
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
        if (p.getMembership() != null) {
            removeMembership(p.getMembership());
        }
        //removeMembership(p.getMembership());
        banList.add(p);
    }

    /*public void addMembership(Player p) {
        try {
            if (p == null) {
                throw new memberAddingExeption("Cannot add a null player.");
            }
            if (banList.contains(p)) {
                throw new memberAddingExeption("This player is banned from the clan");
            }
            if (memberships.stream().anyMatch(m -> m.getPlayer().equals(p))) {
                throw new memberAddingExeption("This player is already in the clan");
            }

            Membership m = new Membership(ClanRole.MEMBER, LocalDate.now(), this, p);

            p.setMembership(m);

        } catch (memberAddingExeption e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }*/

    public void setMemberships(Membership m){memberships.add(m);}



    public void removeMembership(Membership membership){
        try{
            if(memberships.contains(membership)){
                memberships.remove(membership);
            }
            else{
                throw new IlligalVillageExeption("There is no such member in this clan");
            }
        }catch(IlligalVillageExeption e){
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

    public void removeClanWar(ClanWar clanWar){
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
    }

    public static void saveExtent(Path file) {
        ExtentPersistence.saveExtent(EXTENT, file);
    }

    public static void loadExtent(Path file) {
        EXTENT = ExtentPersistence.loadExtent(file);
    }

    public static void deleteExtent(Path file) {
        ExtentPersistence.deleteExtent(file);
        EXTENT.clear();
    }

}
