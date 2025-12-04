package com.example.clashofclans.TestClanWarBattleMembership;

import com.example.clashofclans.clanRelated.Clan;
import com.example.clashofclans.clanRelated.ClanWar;
import com.example.clashofclans.exceptions.clanwar.InvalidDurationException;
import com.example.clashofclans.exceptions.clanwar.InvalidResultException;
import com.example.clashofclans.exceptions.clanwar.InvalidRewardException;
import com.example.clashofclans.exceptions.clanwar.InvalidTimestampException;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClanWarTest {

    @BeforeEach
    void resetExtent() throws Exception {
        var extentField = ClanWar.class.getDeclaredField("extent");
        extentField.setAccessible(true);
        ((List<?>) extentField.get(null)).clear();
    }

    @Test
    void testValidClanWarCreation() {
        Clan clan = new Clan("Warriors", "A strong clan");
        Clan clan1 = new Clan("hello world", "A weak clan");
        LocalDateTime now = LocalDateTime.now();

        ClanWar war = new ClanWar(30, 500, 1, now ,clan , clan1);

        assertEquals(30, war.getDuration());
        assertEquals(500, war.getReward());
        assertEquals(1, war.getResult());
        assertEquals(now, war.getTimestamp());
    }

    @Test
    void testInvalidDurationThrowsException() {
        Clan clan = new Clan("Warriors", "A strong clan");
        Clan clan1 = new Clan("hello world", "A weak clan");

        LocalDateTime now = LocalDateTime.now();

        assertThrows(InvalidDurationException.class,
                () -> new ClanWar(0, 500, 1, now ,clan , clan1));
    }

    @Test
    void testInvalidRewardThrowsException() {
        Clan clan = new Clan("Warriors", "A strong clan");
        Clan clan1 = new Clan("hello world", "A weak clan");

        LocalDateTime now = LocalDateTime.now();

        assertThrows(InvalidRewardException.class,
                () -> new ClanWar(30, -10, 1, now ,clan , clan1));
    }

    @Test
    void testInvalidResultThrowsException() {
        Clan clan = new Clan("Warriors", "A strong clan");
        Clan clan1 = new Clan("hello world", "A weak clan");

        LocalDateTime now = LocalDateTime.now();

        assertThrows(InvalidResultException.class,
                () -> new ClanWar(30, 500, 7, now ,clan , clan1));
    }

    @Test
    void testNullTimestampThrowsException() {
        Clan clan = new Clan("Warriors", "A strong clan");
        Clan clan1 = new Clan("hello world", "A weak clan");

        assertThrows(InvalidTimestampException.class,
                () -> new ClanWar(30, 500, 1, null ,clan , clan1));
    }

    @Test
    void testExtentStoresCreatedObjects() {
        Clan clan = new Clan("Warriors", "A strong clan");
        Clan clan1 = new Clan("hello world", "A weak clan");

        Clan clan2 = new Clan("addssas", "A strong clan");
        Clan clan3 = new Clan("sadasd", "A weak clan");
        LocalDateTime now = LocalDateTime.now();

        ClanWar war1 = new ClanWar(30, 500, 1, now ,clan , clan1);
        ClanWar war2 = new ClanWar(40, 700, 0, now.plusDays(1) , clan2 , clan3);

        List<ClanWar> extent = ClanWar.getExtent();

        assertEquals(2, extent.size());
        assertTrue(extent.contains(war1));
        assertTrue(extent.contains(war2));
    }

    @Test
    void testExtentIsUnmodifiable() {
        Clan clan = new Clan("Warriors", "A strong clan");
        Clan clan1 = new Clan("hello world", "A weak clan");

        LocalDateTime now = LocalDateTime.now();
        new ClanWar(30, 400, 1, now ,clan , clan1);

        List<ClanWar> extent = ClanWar.getExtent();

        assertThrows(UnsupportedOperationException.class, extent::clear);
    }

    @Test
    void testSetInvalidDuration() {
        Clan clan = new Clan("Warriors", "A strong clan");
        Clan clan1 = new Clan("hello world", "A weak clan");

        ClanWar war = new ClanWar(30, 200, 0, LocalDateTime.now() ,clan , clan1);

        assertThrows(InvalidDurationException.class,
                () -> war.setDuration(0));
    }

    @Test
    void testSetInvalidReward() {
        Clan clan = new Clan("Warriors", "A strong clan");
        Clan clan1 = new Clan("hello world", "A weak clan");
        ClanWar war = new ClanWar(30, 200, 0, LocalDateTime.now() ,clan , clan1);

        assertThrows(InvalidRewardException.class,
                () -> war.setReward(-5));
    }

    @Test
    void testSetInvalidResult() {
        Clan clan = new Clan("Warriors", "A strong clan");
        Clan clan1 = new Clan("hello world", "A weak clan");
        ClanWar war = new ClanWar(30, 200, 0, LocalDateTime.now() ,clan , clan1);

        assertThrows(InvalidResultException.class,
                () -> war.setResult(9));
    }

    @Test
    void testSetNullTimestamp() {
        Clan clan = new Clan("Warriors", "A strong clan");
        Clan clan1 = new Clan("hello world", "A weak clan");
        ClanWar war = new ClanWar(30, 200, 0, LocalDateTime.now() ,clan , clan1);

        assertThrows(InvalidTimestampException.class,
                () -> war.setTimestamp(null));
    }
}