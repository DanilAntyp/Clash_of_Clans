
import com.example.clashofclans.*;
import com.example.clashofclans.enums.*;
import com.example.clashofclans.exceptions.unitExceptions.InvalidUnitArgumentException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UnitModelTests {

    Village village = new Village(VillageType.regular, new Player("demo"));

    @Test
    void createValidTroop_elixirGround_ok() {
        int before = Unit.getExtent().size();
        Troop troop = new Troop(village,
                100, 20, 5,
                AttackDomain.GROUND, ResourceKind.ELIXIR, UnitType.BARBARIAN,
                AttackStyle.GROUND_TROOP, 50
        );
        assertEquals(ResourceKind.ELIXIR, troop.getResourceKind());
        assertEquals(AttackDomain.GROUND, troop.getAttackDomain());
        assertEquals(UnitType.BARBARIAN, troop.getType());
        assertEquals(before + 1, Unit.getExtent().size());
        assertEquals(50, troop.getElixirCost());
        assertNull(troop.getDarkElixirCost());
    }

    @Test
    void typeResourceMismatch_throws() {
        assertThrows(InvalidUnitArgumentException.class, () -> new Troop(village,
                50, 10, 5,
                AttackDomain.GROUND, ResourceKind.DARK_ELIXIR, UnitType.BARBARIAN,
                AttackStyle.GROUND_TROOP, 30
        ));
    }

    @Test
    void typeDomainMismatch_throws() {
        assertThrows(InvalidUnitArgumentException.class, () -> new Troop(village,
                120, 35, 20,
                AttackDomain.GROUND, ResourceKind.ELIXIR, UnitType.DRAGON,
                AttackStyle.RANGED_TROOP, 12000
        ));
    }

    @Test
    void negativeNumbers_throws() {
        assertThrows(InvalidUnitArgumentException.class, () -> new Troop(village,
                -10, 10, 1,
                AttackDomain.GROUND, ResourceKind.ELIXIR, UnitType.GOBLIN,
                AttackStyle.GROUND_TROOP, 1
        ));
        assertThrows(InvalidUnitArgumentException.class, () -> new Troop(village,
                10, -1, 1,
                AttackDomain.GROUND, ResourceKind.ELIXIR, UnitType.GOBLIN,
                AttackStyle.GROUND_TROOP, 1
        ));
        assertThrows(InvalidUnitArgumentException.class, () -> new Troop(village,
                10, 1, -1,
                AttackDomain.GROUND, ResourceKind.ELIXIR, UnitType.GOBLIN,
                AttackStyle.GROUND_TROOP, 1
        ));
    }

    @Test
    void heroEmptyStrings_throws() {
        assertThrows(InvalidUnitArgumentException.class, () -> new Hero(village,
                1000, 50, 25,
                AttackDomain.GROUND, ResourceKind.DARK_ELIXIR, UnitType.MINION_KING,
                "  ", 300, "upgrade"
        ));
        assertThrows(InvalidUnitArgumentException.class, () -> new Hero(village,
                1000, 50, 25,
                AttackDomain.GROUND, ResourceKind.DARK_ELIXIR, UnitType.BARBARIAN_KING,
                "Rage", 300, " "
        ));
    }

    @Test
    void derivedRange_ok() {
        Troop air = new Troop(village,
                60, 15, 2,
                AttackDomain.AIR, ResourceKind.DARK_ELIXIR, UnitType.MINION,
                AttackStyle.RANGED_TROOP, 5
        );
        Troop ground = new Troop(village,
                100, 20, 5,
                AttackDomain.GROUND, ResourceKind.ELIXIR, UnitType.ARCHER,
                AttackStyle.RANGED_TROOP, 50
        );
        assertEquals(100, air.getRange());
        assertEquals(50, ground.getRange());
    }

    @Test
    void availableOncePerPlayer_constants_ok() {
        assertFalse(Troop.availableOncePerPlayer);
        assertTrue(Hero.availableOncePerPlayer);
    }

    @Test
    void extentIsUnmodifiable() {
        List<Unit> view = Unit.getExtent();
        assertThrows(UnsupportedOperationException.class, () -> view.add(null));
    }

    @Test
    void persistency_saveAndLoad_ok(@TempDir Path tmp) {
        new Troop(village,100, 10, 5, AttackDomain.GROUND, ResourceKind.ELIXIR, UnitType.BARBARIAN,
                AttackStyle.GROUND_TROOP, 25);
        new Troop(village,70, 18, 2, AttackDomain.AIR, ResourceKind.DARK_ELIXIR, UnitType.MINION,
                AttackStyle.RANGED_TROOP, 15);

        int countBefore = Unit.getExtent().size();
        Path f = tmp.resolve("units.json");
        Unit.saveExtent(f);

        Unit.loadExtent(f);
        assertEquals(countBefore, Unit.getExtent().size());

        boolean hasTroop = Unit.getExtent().stream().anyMatch(u -> u instanceof Troop);
        assertTrue(hasTroop);
    }

    @Test
    void troopWithHeroType_throws() {
        assertThrows(InvalidUnitArgumentException.class, () -> new Troop(village,
                100, 20, 5,
                AttackDomain.GROUND, ResourceKind.DARK_ELIXIR, UnitType.BARBARIAN_KING,
                AttackStyle.GROUND_TROOP, 1
        ));
    }

    @Test
    void heroWithNonHeroType_throws() {
        assertThrows(InvalidUnitArgumentException.class, () -> new Hero(village,
                1000, 50, 25,
                AttackDomain.GROUND, ResourceKind.ELIXIR, UnitType.BARBARIAN,
                "Rage", 300, "upgrade"
        ));
    }

    @Test
    void barbarianKing_isElixirGround_only() {
        new Hero(village,1200, 250, 25,
                AttackDomain.GROUND, ResourceKind.ELIXIR, UnitType.BARBARIAN_KING,
                "Rage", 300, "upgrade");

        assertThrows(InvalidUnitArgumentException.class, () -> new Hero(village,
                1200, 250, 25,
                AttackDomain.AIR, ResourceKind.ELIXIR, UnitType.BARBARIAN_KING,
                "Rage", 300, "upgrade"
        ));

        assertThrows(InvalidUnitArgumentException.class, () -> new Hero(
                village,1200, 250, 25,
                AttackDomain.GROUND, ResourceKind.DARK_ELIXIR, UnitType.BARBARIAN_KING,
                "Rage", 300, "upgrade"
        ));
    }

    @Test
    void grandWarden_isElixirAir_only() {
        new Hero(village,1000, 200, 25,
                AttackDomain.AIR, ResourceKind.ELIXIR, UnitType.GRAND_WARDEN,
                "Life Aura", 450, "upgrade");

        assertThrows(InvalidUnitArgumentException.class, () -> new Hero(
                village,1000, 200, 25,
                AttackDomain.GROUND, ResourceKind.ELIXIR, UnitType.GRAND_WARDEN,
                "Life Aura", 450, "upgrade"
        ));
        assertThrows(InvalidUnitArgumentException.class, () -> new Hero(
                village,1000, 200, 25,
                AttackDomain.AIR, ResourceKind.DARK_ELIXIR, UnitType.GRAND_WARDEN,
                "Life Aura", 450, "upgrade"
        ));
    }

    @Test
    void nullAspects_throws() {
        assertThrows(InvalidUnitArgumentException.class, () -> new Troop(village,
                50, 10, 1,
                null, ResourceKind.ELIXIR, UnitType.BARBARIAN,
                AttackStyle.GROUND_TROOP, 1
        ));
        assertThrows(InvalidUnitArgumentException.class, () -> new Troop(village,
                50, 10, 1,
                AttackDomain.GROUND, null, UnitType.BARBARIAN,
                AttackStyle.GROUND_TROOP, 1
        ));
        assertThrows(InvalidUnitArgumentException.class, () -> new Troop(village,
                50, 10, 1,
                AttackDomain.GROUND, ResourceKind.ELIXIR, null,
                AttackStyle.GROUND_TROOP, 1
        ));
    }
    @Test
    void setAttackStyle_null_throws() {
        Troop t = new Troop(village,60, 12, 2,
                AttackDomain.GROUND, ResourceKind.ELIXIR, UnitType.ARCHER,
                AttackStyle.RANGED_TROOP, 50);
        assertThrows(InvalidUnitArgumentException.class, () -> t.setAttackStyle(null));
    }

    @Test
    void elixirTroop_hasOnlyElixirCost() {
        Troop t = new Troop(village,90, 22, 4,
                AttackDomain.GROUND, ResourceKind.ELIXIR, UnitType.GIANT,
                AttackStyle.GROUND_TROOP, 250);
        assertEquals(250, t.getElixirCost());
        assertNull(t.getDarkElixirCost());
    }

    @Test
    void darkTroop_hasOnlyDarkCost() {
        Troop t = new Troop(village,70, 15, 3,
                AttackDomain.AIR, ResourceKind.DARK_ELIXIR, UnitType.MINION,
                AttackStyle.RANGED_TROOP, 12);
        assertNull(t.getElixirCost());
        assertEquals(12, t.getDarkElixirCost());
    }



}
