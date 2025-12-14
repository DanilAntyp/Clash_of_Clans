package com.example.clashofclans.playerRelatedTests;

import com.example.clashofclans.exceptions.player.InvalidEntryException;
import com.example.clashofclans.exceptions.player.NullEntryExeption;
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
    void testGetSpellsEmptyInitially() {
        Player player = new Player("Test");
        assertNotNull(player.getSpells());
        assertEquals(0, player.getSpells().size());
    }

    @Test
    void addSpellSuccessfully() {
        Player player = new Player("Test");
        Spell spell = new Spell(SpellType.healing, 10, 5.0);
        player.addNewSpell(spell);

        assertEquals(1, player.getSpells().size());
        assertTrue(player.getSpells().contains(spell));
        assertTrue(spell.getPlayers().contains(player));
    }

    @Test
    void addSpellNullError() {
        Player player = new Player("Test");
        assertThrows(NullEntryExeption.class,
                () -> player.addNewSpell(null));
    }

    @Test
    void addSpellDuplicateError() {
        Player player = new Player("Test");

        Spell spell = new Spell(SpellType.healing, 10, 5.0);
        player.addNewSpell(spell);
        assertThrows(duplicateEntryExeption.class,
                () -> player.addNewSpell(spell));
    }

    @Test
    void removeSpellSuccessfully() {
        Player player = new Player("Test");
        Spell spell = new Spell(SpellType.healing, 10, 5.0);
        player.addNewSpell(spell);
        player.removeSpell(spell);

        assertFalse(player.getSpells().contains(spell));
        assertFalse(spell.getPlayers().contains(player));
    }

    @Test
    void removeSpellException() {
        Player player = new Player("Test");
        Spell spell = new Spell(SpellType.healing, 10, 5.0);
        assertThrows(InvalidEntryException.class,
                () -> player.removeSpell(spell));
    }

    @Test
    void removeSpellNullException() {
        Player player = new Player("Test");
        Spell spell = new Spell(SpellType.healing, 10, 5.0);
        assertThrows(NullEntryExeption.class,
                () -> player.removeSpell(null));
    }

    @Test
    void addAchivementSuccessfully() {
        Player player = new Player("Test");
        Achievement achivement = new Achievement("s","a","a","a");
        player.addNewAchivement(achivement);

        assertEquals(1, player.getAchievements().size());
        assertTrue(player.getAchievements().contains(achivement));
        assertTrue(achivement.getPlayers().contains(player));
    }

    @Test
    void addAchivementlNullError() {
        Player player = new Player("Test");
        assertThrows(NullEntryExeption.class,
                () -> player.addNewAchivement(null));
    }

    @Test
    void addAchievemntDuplicateError() {
        Player player = new Player("Test");
        Achievement achivement = new Achievement("s","a","a","a");

        player.addNewAchivement(achivement);
        assertThrows(duplicateEntryExeption.class,
                () -> player.addNewAchivement(achivement));
    }

    @Test
    void removeAchievementSuccessfully() {
        Player player = new Player("Test");
        Achievement achivement = new Achievement("s","a","a","a");
        player.addNewAchivement(achivement);
        player.removeAchivement(achivement);

        assertFalse(player.getAchievements().contains(achivement));
        assertFalse(achivement.getPlayers().contains(player));
    }

    @Test
    void removeAchievementException() {
        Player player = new Player("Test");
        Achievement achivement = new Achievement("s","a","a","a");
        assertThrows(InvalidEntryException.class,
                () -> player.removeAchivement(achivement));
    }

    @Test
    void removeAchievemntNullException() {
        Player player = new Player("Test");
        assertThrows(NullEntryExeption.class,
                () -> player.removeAchivement(null));
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
