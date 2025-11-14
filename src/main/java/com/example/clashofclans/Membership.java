package com.example.clashofclans;

import com.example.clashofclans.enums.ClanRole;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Membership implements Serializable {


    private static final List<Membership> extent = new ArrayList<>();

    private static void addToExtent(Membership m) {
        if (m == null) throw new IllegalArgumentException("Membership cannot be null");
        extent.add(m);
    }

    public static List<Membership> getExtent() {
        return Collections.unmodifiableList(extent);
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ClanRole clanRole;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate joinDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateEnd;

    private boolean isBanned;



    public Membership(ClanRole role, LocalDate joinDate) {
        setClanRole(role);
        setJoinDate(joinDate);

        this.isBanned = false;
        this.dateEnd = null;

        addToExtent(this);
    }

    public Membership() {}

    public void setClanRole(ClanRole role) {
        if (role == null)
            throw new IllegalArgumentException("Clan role cannot be null");
        this.clanRole = role;
    }

    public void setJoinDate(LocalDate joinDate) {
        if (joinDate == null)
            throw new IllegalArgumentException("Join date cannot be null");

        this.joinDate = joinDate;
    }

    public void setDateEnd(LocalDate dateEnd) {
        if (dateEnd != null && dateEnd.isBefore(joinDate))
            throw new IllegalArgumentException("End date cannot be earlier than join date");

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
}