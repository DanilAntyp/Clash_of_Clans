package com.example.clashofclans.playerRelatedTests;

import com.example.clashofclans.Achievement;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestAchivment {

    @Test
    void testAchievementConstructorAndGetters() {
        Achievement a = new Achievement("Winner", "Win 1 battle", "Battle", "100 gold");

        assertEquals("Winner", a.getName());
        assertEquals("Win 1 battle", a.getDescription());
        assertEquals("Battle", a.getType());
        assertEquals("100 gold", a.getReward());
    }
}
