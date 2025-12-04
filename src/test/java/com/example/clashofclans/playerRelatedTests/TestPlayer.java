package com.example.clashofclans.playerRelatedTests;

import com.example.clashofclans.theRest.Achievement;
import com.example.clashofclans.theRest.Player;
import com.example.clashofclans.theRest.Spell;
import com.example.clashofclans.enums.SpellType;
import com.example.clashofclans.exceptions.player.duplicateEntryExeption;
import com.example.clashofclans.exceptions.player.wrongFriendAddingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPlayer {

    @Test
    void testPlayerConstructor() {
        Player player = new Player("Mike");

        assertEquals("Mike", player.getUsername());
        assertEquals(1, player.getLevel());
        assertEquals(0, player.getTrophies());
    }

    @Test
    void testSetters() {
        Player player = new Player("Test");

        player.setLevel(5);
        player.setTrophies(300);

        assertEquals(5, player.getLevel());
        assertEquals(300, player.getTrophies());
    }

    @Test
    void testGetAchievementsEmptyInitially() {
        Player player = new Player("Test");
        assertNotNull(player.getAchievements());
        assertEquals(0, player.getAchievements().size());
    }


    @Test
    void testAddNewSpellSuccessfully() {
        Player player = new Player("Test");
        Spell spell = new Spell(SpellType.rage,30,20);
        player.addNewSpell(spell);


        assertTrue(player.getSpells().contains(spell));
        assertEquals(1, player.getSpells().size());
    }

    @Test
    void testAddDuplicateSpellThrows() {
        Player player = new Player("Test");
        Spell spell = new Spell(SpellType.rage,30,20);
        player.addNewSpell(spell);

        duplicateEntryExeption exception = assertThrows(
                duplicateEntryExeption.class,
                () -> player.addNewSpell(spell)
        );

        assertEquals("Spell already exists in users inventory", exception.getMessage());
    }

    @Test
    void testGetSpellsEmptyInitially() {
        Player player = new Player("Test");
        assertNotNull(player.getSpells());
        assertEquals(0, player.getSpells().size());
    }

    @Test
    void testFriendAddingSuccessfully() {
        Player player = new Player("Test");
        Player player2 = new Player("Test2");
        player2.addFriend(player);
        assertTrue(player2.getFriends().contains(player));
    }

    @Test
    void testFriendAddingSelfError() {
        Player player = new Player("Test");
        assertThrows(wrongFriendAddingException.class,() -> player.addFriend(player));
    }



    @Test
    void testFriendRemoveSuccessfully() {
        Player player = new Player("Test");
        Player player2 = new Player("Test2");
        player2.addFriend(player);
        player2.removeFriend(player);
        assertFalse(player.getFriends().contains(player2));
    }

    @Test
    void testFriendRemoveSelfError() {
        Player player = new Player("Test");
        assertThrows(wrongFriendAddingException.class,() -> player.removeFriend(player));
    }



}
