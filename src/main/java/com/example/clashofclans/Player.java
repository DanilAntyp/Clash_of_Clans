package com.example.clashofclans;

import com.example.clashofclans.enums.VillageType;
import com.example.clashofclans.exceptions.clan.clanBanException;
import com.example.clashofclans.exceptions.player.*;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {

    private String username;
    private int level;
    private int trophies;
    private String leauge; //assuming there are only few types of leauge we might make this one enum
    private Membership membership;
    private ArrayList<Achievement> achivements;
    private ArrayList<Spell> spells;
    private Village[]  villages;
    private ArrayList<Player> friends;

    private static List<Player> EXTENT = new ArrayList<>();

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
        this.friends = new ArrayList<>();


         Village village=new Village(VillageType.regular,this);//when new user created they get their village
        this.villages[0]=village;

         EXTENT.add(this);
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

    public ArrayList<Achievement> getAchievements() {return achivements;}
    public void addNewAchievement(Achievement achievement) {achivements.add(achievement);}
    public ArrayList<Spell> getSpells() {return spells;}
    public void addNewSpell(Spell spell) {
         try {
             if(this.spells.contains(spell)){
                 throw new duplicateEntryExeption("Spell already exists in users inventory");
             }
             this.spells.add(spell);
         }catch(duplicateEntryExeption e){
             System.out.println(e.getMessage());
             throw e;
         }
    }

    public void addFriend(Player player) {
        try {
            if(this==player){
                throw new wrongFriendAddingException("You cannot befriend with yourself");
            } else if (!this.friends.contains(player)) {
                this.friends.add(player);
                player.addFriend(this);
            }

        }catch(wrongFriendAddingException e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public ArrayList getFriends() {return friends;}

    public void removeFriend(Player player) {
        try {
            if(this==player){
                throw new wrongFriendAddingException("You cannot remove yourself");
            } else if (this.friends.contains(player)) {
                this.friends.remove(player);
                player.removeFriend(this);
            }

        }catch(wrongFriendAddingException e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public void addVillageDirectForTest(Village v) {
        if (getVillagesCount() >= 2) {
            throw new villageLimitReachedException("Player cannot have more than two villages.");
        }
         if (villages[0] == null) villages[0] = v;
        else villages[1] = v;
    }

    //THIS ONE IS FOR AGREGATION
    public void delete() {
        for (Player friend : new ArrayList<>(friends)) {
            friend.getFriends().remove(this);
        }
        friends.clear();

        for (int i = 0; i < villages.length; i++) {
            villages[i] = null;
        }
        membership = null;
    }

    public static void saveExtent(Path file) {
        ExtentPersistence.saveExtent(EXTENT, file);
    }

    public static void loadExtent(Path file) {
        EXTENT = ExtentPersistence.loadExtent(file);
    }
}
//league is gonna be enum
