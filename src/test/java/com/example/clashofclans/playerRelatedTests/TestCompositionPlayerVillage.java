package com.example.clashofclans.playerRelatedTests;

import com.example.clashofclans.enums.VillageType;
import com.example.clashofclans.theRest.Player;
import com.example.clashofclans.theRest.Village;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestCompositionPlayerVillage {

    private Player p1;
    private Player p2;
    private Village village;
    private Village village2;

    @BeforeEach //cause im lazy
    void setUp() {
        p1 = new Player("Player1");
        p2 = new Player("Player2");
        village=p1.getVillages()[0];
        village2 = new Village(VillageType.regular, p1);

        p1.addFriend(p2);
    }

    @Test
    void testDeleteRemovesPlayerFromExtent() throws Exception {
        p1.delete();

        List<Player> players = p1.getExtent();

        assertFalse(players.contains(p1), "Player should be removed from EXTENT");

    }

    @Test
    void testDeleteRemovesPlayersVillagesFromExtent() throws Exception {
        p1.delete();

        List<Village> villages = village.getExtent();

        assertFalse(villages.contains(village), "Village should be removed from EXTENT");
        assertFalse(villages.contains(village2), "Village should be removed from EXTENT");

    }

    @Test
    void testDeleteRemovesFromFriends() {
        p1.delete();
        assertFalse(p2.getFriends().contains(p1), "Friend list should not contain deleted player");
    }

}
