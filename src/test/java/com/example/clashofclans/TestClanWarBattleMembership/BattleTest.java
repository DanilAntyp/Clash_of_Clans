package com.example.clashofclans.TestClanWarBattleMembership;

import com.example.clashofclans.Battle;
import com.example.clashofclans.Player;
import com.example.clashofclans.Village;
import com.example.clashofclans.enums.BattleType;
import com.example.clashofclans.enums.VillageType;
import com.example.clashofclans.exceptions.battle.InvalidBattleTimeException;
import com.example.clashofclans.exceptions.battle.InvalidBattleTypeException;
import com.example.clashofclans.exceptions.battle.InvalidLootException;
import com.example.clashofclans.exceptions.battle.InvalidStarsException;
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
        Village villageAttacked = new Village(VillageType.regular, new Player("Alice"));
        Village villageDefence = new Village(VillageType.regular, new Player("Dan"));
        LocalDateTime now = LocalDateTime.now();
        Battle b = new Battle(BattleType.SINGLE, now, 3, 500 ,villageAttacked ,villageDefence);

        assertEquals(BattleType.SINGLE, b.getType());
        assertEquals(now, b.getTime());
        assertEquals(3, b.getStars());
        assertEquals(500, b.getLoot());
    }

    @Test
    void testNullTypeThrowsException() {
        Village villageAttacked = new Village(VillageType.regular, new Player("Alice"));
        Village villageDefence = new Village(VillageType.regular, new Player("Dan"));
        LocalDateTime now = LocalDateTime.now();
        assertThrows(InvalidBattleTypeException.class,
                () -> new Battle(null, now, 1, 100 ,villageAttacked ,villageDefence));
    }

    @Test
    void testNullTimeThrowsException() {
        Village villageAttacked = new Village(VillageType.regular, new Player("Alice"));
        Village villageDefence = new Village(VillageType.regular, new Player("Dan"));
        assertThrows(InvalidBattleTimeException.class,
                () -> new Battle(BattleType.CLAN, null, 1, 100, villageAttacked ,villageDefence));
    }

    @Test
    void testInvalidStarsLow() {
        Village villageAttacked = new Village(VillageType.regular, new Player("Alice"));
        Village villageDefence = new Village(VillageType.regular, new Player("Dan"));
        LocalDateTime now = LocalDateTime.now();
        assertThrows(InvalidStarsException.class,
                () -> new Battle(BattleType.SINGLE, now, -1, 100 ,villageAttacked ,villageDefence));
    }

    @Test
    void testInvalidStarsHigh() {
        Village villageAttacked = new Village(VillageType.regular, new Player("Alice"));
        Village villageDefence = new Village(VillageType.regular, new Player("Dan"));
        LocalDateTime now = LocalDateTime.now();
        assertThrows(InvalidStarsException.class,
                () -> new Battle(BattleType.SINGLE, now, 5, 100 ,villageAttacked ,villageDefence));
    }

    @Test
    void testInvalidLootThrowsException() {
        Village villageAttacked = new Village(VillageType.regular, new Player("Alice"));
        Village villageDefence = new Village(VillageType.regular, new Player("Dan"));
        LocalDateTime now = LocalDateTime.now();
        assertThrows(InvalidLootException.class,
                () -> new Battle(BattleType.SINGLE, now, 1, -10 ,villageAttacked ,villageDefence));
    }

    @Test
    void testExtentContainsCreatedBattles() {
        Village villageAttacked = new Village(VillageType.regular, new Player("Alice"));
        Village villageDefence = new Village(VillageType.regular, new Player("Dan"));

        Village villageAttacked1 = new Village(VillageType.regular, new Player("Alice1"));
        Village villageDefence1 = new Village(VillageType.regular, new Player("Dan1"));

        LocalDateTime now = LocalDateTime.now();

        Battle b1 = new Battle(BattleType.SINGLE, now, 2, 200 ,villageAttacked ,villageDefence);
        Battle b2 = new Battle(BattleType.CLAN, now.plusHours(1), 3, 400 ,villageAttacked1 ,villageDefence1);

        List<Battle> extent = Battle.getExtent();

        assertEquals(2, extent.size());
        assertTrue(extent.contains(b1));
        assertTrue(extent.contains(b2));
    }

    @Test
    void testExtentIsUnmodifiable() {
        Village villageAttacked = new Village(VillageType.regular, new Player("Alice"));
        Village villageDefence = new Village(VillageType.regular, new Player("Dan"));

        LocalDateTime now = LocalDateTime.now();
        new Battle(BattleType.SINGLE, now, 1, 100 ,villageAttacked ,villageDefence);

        List<Battle> extent = Battle.getExtent();

        assertThrows(UnsupportedOperationException.class, extent::clear);
    }

    @Test
    void testComputeResults() {
        Village villageAttacked = new Village(VillageType.regular, new Player("Alice"));
        Village villageDefence = new Village(VillageType.regular, new Player("Dan"));

        Battle b = new Battle(BattleType.SINGLE, LocalDateTime.now(), 3, 250 ,villageAttacked ,villageDefence);
        int result = b.computeResults();
        assertEquals(550, result);   // 3*100 + 250
    }
}