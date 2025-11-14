package com.example.clashofclans;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestSpell {

    @Test
    void testSpellConstructorAndGetters() {
        Spell spell = new Spell(SpellType.healing, 50, 2.5);

        assertEquals(SpellType.healing, spell.getType());
        assertEquals(50, spell.getCost());
        assertEquals(2.5, spell.getDuration());
    }
}
