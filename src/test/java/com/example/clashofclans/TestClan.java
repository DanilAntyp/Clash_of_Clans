package com.example.clashofclans;

import com.example.clashofclans.exceptions.clan.clanBanException;
import com.example.clashofclans.exceptions.clan.memberAddingExeption;
import com.example.clashofclans.exceptions.village.illigalRemoveExeption;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    void testAddMemberSuccess(){
        Clan clan = new Clan("Warriors", "A strong clan");
        Player p= new Player("John");
        clan.addMember(p);
        assertTrue(clan.getMemberships().containsKey(p));
    }

    @Test
    void testAddMemberNullPlayer(){
        Clan clan = new Clan("Warriors", "A strong clan");
        assertThrows(memberAddingExeption.class,() -> clan.addMember(null));
    }

    @Test
    void testAddMemberBannedPlayer(){
        Clan clan = new Clan("Warriors", "A strong clan");
        Player p= new Player("John");
        clan.addMember(p);
        clan.addBan(p);
        assertThrows(memberAddingExeption.class,() -> clan.addMember(p));
    }

    @Test
    void testAddMemberTwice(){
        Clan clan = new Clan("Warriors", "A strong clan");
        Player p= new Player("John");
        clan.addMember(p);

        assertThrows(memberAddingExeption.class,() -> clan.addMember(p));
    }


    @Test
    void testRemoveMemberSuccess(){
        Clan clan = new Clan("Warriors", "A strong clan");
        Player p= new Player("John");
        clan.addMember(p);
        clan.removeMember(p);
        assertFalse(clan.getMemberships().containsKey(p));
    }

    @Test
    void testRemoveMemberNotInClan(){
        Clan clan = new Clan("Warriors", "A strong clan");
        Player p= new Player("John");

        assertThrows(illigalRemoveExeption.class,() -> clan.removeMember(p));
    }



    @Test
    void testAddBanSuccessfullyRemovesFromMembers() {
        Clan clan = new Clan("Warriors", "A strong clan");
        Player p= new Player("John");
        clan.addMember(p);
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
