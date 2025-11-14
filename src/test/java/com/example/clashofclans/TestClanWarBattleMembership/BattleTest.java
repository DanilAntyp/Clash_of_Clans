package com.example.clashofclans.TestClanWarBattleMembership;

import com.example.clashofclans.Battle;
import com.example.clashofclans.enums.BattleType;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BattleTest {

    @BeforeEach
    void resetExtent() throws Exception {
        Field extentField = Battle.class.getDeclaredField("extent");
        extentField.setAccessible(true);
        ((List<?>) extentField.get(null)).clear();
    }


    @Test
    void testValidBattleCreation() {
        LocalDateTime now = LocalDateTime.now();
        Battle b = new Battle(BattleType.SINGLE, now, 3, 500);

        assertEquals(BattleType.SINGLE, b.getType());
        assertEquals(now, b.getTime());
        assertEquals(3, b.getStars());
        assertEquals(500, b.getLoot());
    }

    @Test
    void testNullTypeThrowsException() {
        LocalDateTime now = LocalDateTime.now();
        assertThrows(IllegalArgumentException.class,
                () -> new Battle(null, now, 1, 100));
    }

    @Test
    void testNullTimeThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Battle(BattleType.CLAN, null, 1, 100));
    }

    @Test
    void testInvalidStarsLow() {
        LocalDateTime now = LocalDateTime.now();
        assertThrows(IllegalArgumentException.class,
                () -> new Battle(BattleType.SINGLE, now, -1, 100));
    }

    @Test
    void testInvalidStarsHigh() {
        LocalDateTime now = LocalDateTime.now();
        assertThrows(IllegalArgumentException.class,
                () -> new Battle(BattleType.SINGLE, now, 5, 100));
    }

    @Test
    void testInvalidLootThrowsException() {
        LocalDateTime now = LocalDateTime.now();
        assertThrows(IllegalArgumentException.class,
                () -> new Battle(BattleType.SINGLE, now, 1, -10));
    }


    @Test
    void testExtentContainsCreatedBattles() {
        LocalDateTime now = LocalDateTime.now();

        Battle b1 = new Battle(BattleType.SINGLE, now, 2, 200);
        Battle b2 = new Battle(BattleType.CLAN, now.plusHours(1), 3, 400);

        List<Battle> extent = Battle.getExtent();

        assertEquals(2, extent.size());
        assertTrue(extent.contains(b1));
        assertTrue(extent.contains(b2));
    }


    @Test
    void testExtentIsUnmodifiable() {
        LocalDateTime now = LocalDateTime.now();
        new Battle(BattleType.SINGLE, now, 1, 100);

        List<Battle> extent = Battle.getExtent();

        assertThrows(UnsupportedOperationException.class, () -> {
            extent.clear();
        });
    }

    @Test
    void testComputeResults() {
        Battle b = new Battle(BattleType.SINGLE, LocalDateTime.now(), 3, 250);
        int result = b.computeResults();
        assertEquals(550, result);   // 3*100 + 250
    }
}