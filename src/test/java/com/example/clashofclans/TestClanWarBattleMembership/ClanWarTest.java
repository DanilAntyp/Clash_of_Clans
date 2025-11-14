package com.example.clashofclans.TestClanWarBattleMembership;

import com.example.clashofclans.ClanWar;
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
        LocalDateTime now = LocalDateTime.now();

        ClanWar war = new ClanWar(30, 500, 1, now);

        assertEquals(30, war.getDuration());
        assertEquals(500, war.getReward());
        assertEquals(1, war.getResult());
        assertEquals(now, war.getTimestamp());
    }

    @Test
    void testInvalidDurationThrowsException() {
        LocalDateTime now = LocalDateTime.now();

        assertThrows(IllegalArgumentException.class,
                () -> new ClanWar(0, 500, 1, now));
    }

    @Test
    void testInvalidRewardThrowsException() {
        LocalDateTime now = LocalDateTime.now();

        assertThrows(IllegalArgumentException.class,
                () -> new ClanWar(30, -10, 1, now));
    }

    @Test
    void testInvalidResultThrowsException() {
        LocalDateTime now = LocalDateTime.now();

        assertThrows(IllegalArgumentException.class,
                () -> new ClanWar(30, 500, 7, now));
    }

    @Test
    void testNullTimestampThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new ClanWar(30, 500, 1, null));
    }

    @Test
    void testExtentStoresCreatedObjects() {
        LocalDateTime now = LocalDateTime.now();

        ClanWar war1 = new ClanWar(30, 500, 1, now);
        ClanWar war2 = new ClanWar(40, 700, 0, now.plusDays(1));

        List<ClanWar> extent = ClanWar.getExtent();

        assertEquals(2, extent.size());
        assertTrue(extent.contains(war1));
        assertTrue(extent.contains(war2));
    }

    @Test
    void testExtentIsUnmodifiable() {
        LocalDateTime now = LocalDateTime.now();
        new ClanWar(30, 400, 1, now);

        List<ClanWar> extent = ClanWar.getExtent();

        assertThrows(UnsupportedOperationException.class, () -> {
            extent.clear();
        });
    }


    @Test
    void testSetInvalidDuration() {
        ClanWar war = new ClanWar(30, 200, 0, LocalDateTime.now());

        assertThrows(IllegalArgumentException.class, () -> war.setDuration(0));
    }

    @Test
    void testSetInvalidReward() {
        ClanWar war = new ClanWar(30, 200, 0, LocalDateTime.now());

        assertThrows(IllegalArgumentException.class, () -> war.setReward(-5));
    }

    @Test
    void testSetInvalidResult() {
        ClanWar war = new ClanWar(30, 200, 0, LocalDateTime.now());

        assertThrows(IllegalArgumentException.class, () -> war.setResult(9));
    }

    @Test
    void testSetNullTimestamp() {
        ClanWar war = new ClanWar(30, 200, 0, LocalDateTime.now());

        assertThrows(IllegalArgumentException.class, () -> war.setTimestamp(null));
    }
}