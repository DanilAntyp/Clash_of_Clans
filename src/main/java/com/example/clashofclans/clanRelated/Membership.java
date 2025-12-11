package com.example.clashofclans.clanRelated;

import com.example.clashofclans.ExtentPersistence;
import com.example.clashofclans.exceptions.clan.clanBanException;
import com.example.clashofclans.theRest.Player;
import com.example.clashofclans.enums.ClanRole;
import com.example.clashofclans.exceptions.clanwar.NullClanException;
import com.example.clashofclans.exceptions.membership.InvalidClanRoleException;
import com.example.clashofclans.exceptions.membership.InvalidEndDateException;
import com.example.clashofclans.exceptions.membership.InvalidJoinDateException;
import com.example.clashofclans.exceptions.membership.NullMembershipException;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Membership implements Serializable {


    private static List<Membership> extent = new ArrayList<>();

    private static void addToExtent(Membership m) {
        if (m == null) throw new NullMembershipException("Membership cannot be null");
        extent.add(m);
    }

    public static List<Membership> getExtent() {
        return Collections.unmodifiableList(extent);
    }

    private Clan clan;
    private Player player;

    private Long id;

    private ClanRole clanRole;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate joinDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateEnd;

    private boolean isBanned;



    public Membership(ClanRole role, LocalDate joinDate , Clan clan , Player player) {

        if (clan == null || player == null)throw new NullClanException("Clan cannot be null");

        setClanRole(role);
        setJoinDate(joinDate);

        this.isBanned = false;
        this.dateEnd = null;
        this.clan = clan;
        this.player = player;

        if(clan.checkIfBanned(player)) throw new clanBanException("Player is banned from clan");
        clan.setMemberships(this);

        player.setMembership(this);

        addToExtent(this);

    }

    public Membership() {}

    public void setClanRole(ClanRole role) {
        if (role == null)
            throw new InvalidClanRoleException("Clan role cannot be null");
        this.clanRole = role;
    }

    public void setJoinDate(LocalDate joinDate) {
        if (joinDate == null)
            throw new InvalidJoinDateException("Join date cannot be null");

        this.joinDate = joinDate;
    }

    public void setDateEnd(LocalDate dateEnd) {
        if (dateEnd != null && dateEnd.isBefore(joinDate))
            throw new InvalidEndDateException("End date cannot be earlier than join date");

        this.dateEnd = dateEnd;
    }

    public void setIsBanned(boolean banned) {
        this.isBanned = banned;
    }



    public void Join() {
        this.joinDate = LocalDate.now();
        this.dateEnd = null;
        this.isBanned = false;
    }

    public void Leave() {
        this.dateEnd = LocalDate.now();
    }



    public ClanRole getClanRole() {
        return clanRole;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public boolean getIsBanned() {
        return isBanned;
    }

    public Long getId() {
        return id;
    }

    public Clan getClan() {
        return this.clan;
    }

    public Player getPlayer(){return this.player;}

    public static void saveExtent(Path file) {
        ExtentPersistence.saveExtent(extent, file);
    }

    public static void loadExtent(Path file) {
        extent = ExtentPersistence.loadExtent(file);
    }
}