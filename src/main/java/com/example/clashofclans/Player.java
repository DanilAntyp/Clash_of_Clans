package com.example.clashofclans;

import java.util.ArrayList;

public class Player {

    private String username;
    private int level;
    private int trophies;
    private String leauge; //assuming there are only few types of leauge we might make this one enum
    //private Membership membership;
    private ArrayList<Achievement> achivements;
    private ArrayList<Spell> spells;
    private Village[]  villages;

     public Player(String username){
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
        //idk the logic
    }

    public void challangeFriend(Player p){
        //idk the logic
    }

    //setter-getters
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public int getLevel() {
        return level;
    }
    public int getTrophies() {
        return trophies;
    }
    public String getLeauge() {
        return leauge;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public void setTrophies(int trophies) {
        this.trophies = trophies;
    }
    public void setLeauge(String leauge) {
        this.leauge = leauge;
    }
    /*
    public Membership getMembership() {
        return membership;
    }
    public void setMembership(Membership membership) {
        this.membership = membership;
    }
     */
}
