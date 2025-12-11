package com.example.clashofclans;

import com.example.clashofclans.clanRelated.Clan;
import com.example.clashofclans.exceptions.clan.clanBanException;
import com.example.clashofclans.exceptions.clan.memberAddingExeption;
import com.example.clashofclans.exceptions.village.IlligalVillageExeption;
import com.example.clashofclans.theRest.Player;
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
    void testRemoveMembershipNotInClan(){
        Clan clan = new Clan("Warriors", "A strong clan");
        Player p= new Player("John");

        assertThrows(IlligalVillageExeption.class,() -> clan.removeMembership(p.getMembership()));
    }





    @Test
    void testAddBanNullPlayerThrows() {
        Clan clan = new Clan("Warriors", "A strong clan");
        assertThrows(clanBanException.class, () -> clan.addBan(null));
    }

}
