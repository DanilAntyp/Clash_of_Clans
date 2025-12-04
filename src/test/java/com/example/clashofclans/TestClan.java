package com.example.clashofclans;

import com.example.clashofclans.exceptions.clan.clanBanException;
import com.example.clashofclans.exceptions.clan.memberAddingExeption;
import com.example.clashofclans.exceptions.village.illigalRemoveExeption;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/*
public class TestClan {

    @Test
    void testClanConstructorAndGetters() {
        Clan clan = new Clan("Warriors", "A strong clan");

        assertEquals("Warriors", clan.getName());
        assertEquals("A strong clan", clan.getDescription());
        assertEquals(0, clan.getTotalTrophies());
        assertFalse(clan.checkIfBanned(new Player("John"))); // empty banList
    }

    @Test
    void testAddMembershipSuccess(){
        Clan clan = new Clan("Warriors", "A strong clan");
        Player p= new Player("John");
        clan.addMembership(p);
        assertTrue(clan.getMemberships().contains(p));
    }

    @Test
    void testAddMembershipNullPlayer(){
        Clan clan = new Clan("Warriors", "A strong clan");
        assertThrows(memberAddingExeption.class,() -> clan.addMembership(null));
    }

    @Test
    void testAddMembershipBannedPlayer(){
        Clan clan = new Clan("Warriors", "A strong clan");
        Player p= new Player("John");
        clan.addMembership(p);
        clan.addBan(p);
        assertThrows(memberAddingExeption.class,() -> clan.addMembership(p));
    }

    @Test
    void testAddMembershipTwice(){
        Clan clan = new Clan("Warriors", "A strong clan");
        Player p= new Player("John");
        clan.addMembership(p);

        assertThrows(memberAddingExeption.class,() -> clan.addMembership(p));
    }


    @Test
    void testRemoveMembershipSuccess(){
        Clan clan = new Clan("Warriors", "A strong clan");
        Player p= new Player("John");
        clan.addMembership(p);
        clan.removeMembership(p);
        assertFalse(clan.getMemberships().containsKey(p));
    }

    @Test
    void testRemoveMembershipNotInClan(){
        Clan clan = new Clan("Warriors", "A strong clan");
        Player p= new Player("John");

        assertThrows(illigalRemoveExeption.class,() -> clan.removeMembership(p));
    }



    @Test
    void testAddBanSuccessfullyRemovesFromMembers() {
        Clan clan = new Clan("Warriors", "A strong clan");
        Player p= new Player("John");
        clan.addMembership(p);
        assertTrue(clan.getMemberships().containsKey(p));

        clan.addBan(p);
        assertFalse(clan.getMemberships().containsKey(p));
        assertTrue(clan.getBanList().contains(p));
    }

    @Test
    void testAddBanNullPlayerThrows() {
        Clan clan = new Clan("Warriors", "A strong clan");
        assertThrows(clanBanException.class, () -> clan.addBan(null));
    }

    @Test
    void testAddBanAlreadyBannedPlayerDoesNothing() {
        Clan clan = new Clan("Warriors", "A strong clan");
        Player p= new Player("John");
        clan.addBan(p);
        int sizeBefore = clan.getBanList().size();
        clan.addBan(p); // second call should do nothing
        assertEquals(sizeBefore, clan.getBanList().size());
    }
}
*/