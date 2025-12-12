package com.example.clashofclans;

import com.example.clashofclans.clanRelated.Clan;
import com.example.clashofclans.clanRelated.ClanWar;
import com.example.clashofclans.clanRelated.Membership;
import com.example.clashofclans.enums.ClanRole;
import com.example.clashofclans.exceptions.clan.calnWarAddingExemption;
import com.example.clashofclans.exceptions.clan.clanBanException;
import com.example.clashofclans.exceptions.clan.memberAddingExeption;
import com.example.clashofclans.exceptions.clanwar.NullClanException;
import com.example.clashofclans.exceptions.village.IlligalVillageExeption;
import com.example.clashofclans.theRest.Player;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TestClan {

    @Test
    void testClanConstructorAndGetters() {
        Clan clan = new Clan("Warriors", "A strong clan");

        assertEquals("Warriors", clan.getName());
        assertEquals("A strong clan", clan.getDescription());
        assertEquals(0, clan.getTotalTrophies());
        assertFalse(clan.checkIfBanned(new Player("John")));
    }




    @Test
    void testRemoveMembershipNotInClan(){
        Clan clan = new Clan("Warriors", "A strong clan");
        Player p= new Player("John");

        assertThrows(IlligalVillageExeption.class,() -> clan.removeMembership(p.getMembership()));
    }


    @Test
    void testAddMembershipViaConstructor() {
        Clan clan = new Clan("Warriors", "desc");
        Player player = new Player("Alice");

        Membership m = new Membership(
                ClanRole.MEMBER,
                LocalDate.now(),
                clan,
                player
        );

        assertEquals(1, clan.getMemberships().size());
        assertEquals(clan, player.getMembership().getClan());
    }

    @Test
    void testRemoveMembershipSuccessfully() {
        Clan clan = new Clan("Warriors", "desc");
        Player player = new Player("Alice");

        Membership m = new Membership(
                ClanRole.MEMBER,
                LocalDate.now(),
                clan,
                player
        );

        clan.removeMembership(m);

        assertEquals(0, clan.getMemberships().size());
        assertNull(player.getMembership());
    }

    @Test
    void testRemovingNonExistingMembershipThrows() {
        Clan clan = new Clan("Warriors", "desc");
        Player p = new Player("Ghost");
        assertThrows(IlligalVillageExeption.class,
                () -> clan.removeMembership(p.getMembership()));
    }



    @Test
    void testAddBanNullPlayerThrows() {
        Clan clan = new Clan("Warriors", "A strong clan");
        assertThrows(clanBanException.class, () -> clan.addBan(null));
    }

    @Test
    void testAddBanRemovesMembershipIfPlayerInClan() {
        Clan clan = new Clan("Warriors", "desc");
        Player player = new Player("Bob");

        Membership m = new Membership(
                ClanRole.MEMBER,
                LocalDate.now(),
                clan,
                player
        );

        clan.addBan(player);

        assertTrue(clan.getBanList().contains(player));
        assertEquals(0, clan.getMemberships().size());
        assertNull(player.getMembership());
    }

    @Test
    void testAddBanAlreadyBannedDoesNotThrow() {
        Clan clan = new Clan("Warriors", "desc");
        Player player = new Player("Bob");

        clan.addBan(player);
        clan.addBan(player);

        assertEquals(1, clan.getBanList().size());
    }


    @Test
    void testAddClanWar() {
        Clan clan = new Clan("Warriors", "desc");
        Clan enemy = new Clan("Enemies", "desc");


        ClanWar cw = new ClanWar(
                5, 100, 1, LocalDateTime.now(), clan, enemy
        );

        assertEquals(1, clan.getClanWars().size());
    }

    @Test
    void testAddNullClanWarThrows() {
        Clan clan = new Clan("Warriors", "desc");
        assertThrows(
                calnWarAddingExemption.class,
                () -> clan.addClanWar(null)
        );
    }

    @Test
    void testAddDuplicateClanWarThrows() {
        Clan clan = new Clan("Warriors", "desc");
        Clan enemy = new Clan("Enemies", "desc");

        ClanWar cw = new ClanWar(
                5, 100, 1, LocalDateTime.now(), clan, enemy
        );

        // Adding again should throw
        assertThrows(
                calnWarAddingExemption.class,
                () -> clan.addClanWar(cw)
        );
    }

    @Test
    void testRemoveExistingClanWar() {
        Clan clan = new Clan("Warriors", "desc");
        Clan enemy = new Clan("Enemies", "desc");

        ClanWar cw = new ClanWar(
                5, 100, 1, LocalDateTime.now(), clan, enemy
        );

        assertThrows(NullClanException.class, () -> clan.removeClanWar(cw));
    }

    @Test
    void testRemoveNonExistingClanWarThrows() {
        Clan clan = new Clan("Warriors", "desc");
        Clan enemy = new Clan("Enemies", "desc");

        ClanWar cw = new ClanWar(
                5, 100, 1, LocalDateTime.now(), new Clan("Other", "d"), enemy
        );

        assertThrows(
                calnWarAddingExemption.class,
                () -> clan.removeClanWar(cw)
        );
    }



}
