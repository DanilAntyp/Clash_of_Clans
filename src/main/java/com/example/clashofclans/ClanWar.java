package com.example.clashofclans;

import com.example.clashofclans.exceptions.battle.InvalidBattleTimeException;
import com.example.clashofclans.exceptions.battle.NullBattleException;
import com.example.clashofclans.exceptions.clanwar.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class ClanWar implements Serializable {

    private static final List<ClanWar> extent = new ArrayList<>();

    private TreeSet<Clan> clans = new TreeSet<>();

    private Map<LocalDateTime , Battle>  battlesInClanWar= new Hashtable<>();


    public static List<ClanWar> getExtent() {
        return Collections.unmodifiableList(extent);
    }

    private static void addToExtent(ClanWar war) {
        if (war == null) throw new NullClanWarException("ClanWar cannot be null");
        extent.add(war);
    }

    private Long id;

    private int duration;
    private int reward;
    private int result;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime timestamp;

    public ClanWar(int duration, int reward, int result, LocalDateTime timestamp , Clan clan1 , Clan clan2) {
        if(clan1 == null || clan2 == null) throw new NullClanException("Clan cannot be null");

        setDuration(duration);
        setReward(reward);
        setResult(result);
        setTimestamp(timestamp);

        addClan(clan1);
        addClan(clan2);

        addToExtent(this);
    }

    public ClanWar() {}


    public void setDuration(int duration) {
        if (duration <= 0)
            throw new InvalidDurationException("Duration must be positive");
        this.duration = duration;
    }

    public void setReward(int reward) {
        if (reward < 0)
            throw new InvalidRewardException("Reward must be non-negative");
        this.reward = reward;
    }

    public void setResult(int result) {
        if (result != 0 && result != 1)
            throw new InvalidResultException("Result must be 0 (loss) or 1 (win)");
        this.result = result;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        if (timestamp == null)
            throw new InvalidTimestampException("Timestamp cannot be null");
        this.timestamp = timestamp;
    }

    public int getDuration() {
        return duration;
    }

    public int getReward() {
        return reward;
    }

    public int getResult() {
        return result;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Long getId() {
        return id;
    }

    public void addClan(Clan clan) {
        if (!this.clans.contains(clan)) {

            this.clans.add(clan);

            clan.addClanWar(this);
        }
    }

    public void removeClan(Clan clan ){
        if(clan == null) throw new NullClanException("Clan cannot be null");

        if(this.clans.contains(clan)){
            if (this.clans.size() <=2){
                throw new NullClanException("Cannot remove clan from clan war with less than 2 clans.");
            }
            this.clans.remove(clan);
            clan.removeClanWar(this);
        }

    }

    public void addBattle(Battle battle) {

        if(battle == null) throw new NullBattleException("Battle cannot be null");

        LocalDateTime time  = battle.getTime();

        if(time == null) throw new InvalidBattleTimeException("Battle time cannot be null");


        if(this.battlesInClanWar.containsKey(time)){
            throw new RuntimeException("A battle is already scheduled at " + time.toString());
        }
        this.battlesInClanWar.put(time , battle);

        battle.addClanWar(this);

    }
    public void removeBattle(Battle battle) {

        if(battle == null) throw new NullBattleException("Battle cannot be null");
        LocalDateTime time  = battle.getTime();

        if(this.battlesInClanWar.containsKey(time)){
            this.battlesInClanWar.remove(time);

            battle.removeClanWar(this);
        }
    }

    public Battle getBattle(LocalDateTime time){
        return this.battlesInClanWar.get(time);
    }

}