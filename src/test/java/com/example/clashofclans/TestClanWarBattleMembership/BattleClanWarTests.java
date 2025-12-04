package com.example.clashofclans.TestClanWarBattleMembership;

import com.example.clashofclans.clanRelated.Clan;
import com.example.clashofclans.clanRelated.ClanWar;
import com.example.clashofclans.enums.BattleType;
import com.example.clashofclans.enums.VillageType;
import com.example.clashofclans.theRest.Battle;
import com.example.clashofclans.theRest.Player;
import com.example.clashofclans.theRest.Village;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BattleClanWarTests {
	private ClanWar clanWar1;
	private ClanWar clanWar2;
	private Battle battle;
	private LocalDateTime time;

	@BeforeEach
	void setUp() throws Exception {
		Field warExtent = ClanWar.class.getDeclaredField("extent");
		warExtent.setAccessible(true);
		((List<?>) warExtent.get(null)).clear();

		Field battleExtent = Battle.class.getDeclaredField("extent");
		battleExtent.setAccessible(true);
		((List<?>) battleExtent.get(null)).clear();


		Clan c1 = new Clan("Clan1", "Desc");
		Clan c2 = new Clan("Clan2", "Desc");
		Village v1 = new Village(VillageType.regular,new Player("Dan"));
		Village v2 = new Village(VillageType.regular ,new Player("SIGMA"));

		time = LocalDateTime.of(2025, 1, 1, 12, 0);

		clanWar1 = new ClanWar(48, 1000, 0, LocalDateTime.now(), c1, c2);
		clanWar2 = new ClanWar(24, 500, 0, LocalDateTime.now().plusDays(1), c1, c2);

		battle = new Battle(BattleType.SINGLE, time, 3, 5000, v1, v2);
	}

	private ClanWar getClanWarFromBattle(Battle b) throws Exception {
		Field field = Battle.class.getDeclaredField("clanWar");
		field.setAccessible(true);
		return (ClanWar) field.get(b);
	}

	@Test
	void testAddBattle_UpdatesReverseConnectionInBattle() throws Exception {
		clanWar1.addBattle(battle);

		assertEquals(battle, clanWar1.getBattle(time));

		ClanWar reverseRef = getClanWarFromBattle(battle);
		assertNotNull(reverseRef, "Battle should have a reference to the ClanWar");
		assertEquals(clanWar1, reverseRef, "Battle should point back to the specific ClanWar");
	}

	@Test
	void testAddClanWar_UpdatesReverseConnectionInClanWar() throws Exception {
		battle.addClanWar(clanWar1);

		assertEquals(clanWar1, getClanWarFromBattle(battle));

		Battle reverseRef = clanWar1.getBattle(time);
		assertNotNull(reverseRef, "ClanWar should have received the Battle via reverse connection");
		assertEquals(battle, reverseRef);
	}

	@Test
	void testRemoveBattle_ClearsReverseConnection() throws Exception {
		clanWar1.addBattle(battle);

		assertNotNull(getClanWarFromBattle(battle));

		clanWar1.removeBattle(battle);

		assertNull(clanWar1.getBattle(time));

		assertNull(getClanWarFromBattle(battle), "Battle's reference to ClanWar should be cleared");
	}

	@Test
	void testSwitchingWars_UpdatesAllConnections() throws Exception {
		clanWar1.addBattle(battle);
		assertEquals(clanWar1, getClanWarFromBattle(battle));

		battle.addClanWar(clanWar2);

		assertEquals(clanWar2, getClanWarFromBattle(battle));

		assertEquals(battle, clanWar2.getBattle(time));

		assertNull(clanWar1.getBattle(time), "Old War should verify battle was removed");
	}

	@Test
	void testDuplicateAdd_DoesNotCauseInfiniteRecursion() {
		clanWar1.addBattle(battle);

		clanWar1.addBattle(battle);
		battle.addClanWar(clanWar1);

		assertTrue(true);
	}
}
