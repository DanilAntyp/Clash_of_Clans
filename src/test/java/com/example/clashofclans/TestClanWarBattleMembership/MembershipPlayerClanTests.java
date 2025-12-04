package com.example.clashofclans.TestClanWarBattleMembership;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import com.example.clashofclans.clanRelated.Clan;
import com.example.clashofclans.clanRelated.Membership;
import com.example.clashofclans.enums.ClanRole;
import com.example.clashofclans.theRest.Player;
import static org.junit.jupiter.api.Assertions.*;

public class MembershipPlayerClanTests {

	private Clan clan ;
	private Player player ;

	@BeforeEach
	void setUp() throws Exception{
		clearExtent(Membership.class);
		clearExtent(Clan.class);
		clearExtent(Player.class);

		clan =new Clan("Test" , "Desc");
		player = new Player ("Danila lox");
	}

	private void clearExtent(Class<?> clazz) throws Exception {
		Field extent = clazz.getDeclaredField("EXTENT");
		extent.setAccessible(true);
		((List<?>) extent.get(null)).clear();
	}

	@Test
	void testMembershipConstructor_CreatesReverseConnection() {
		Membership m = new Membership(ClanRole.MEMBER, LocalDate.now(), clan, player);

		assertEquals(clan, m.getClan());
		assertEquals(player, m.getPlayer());

		assertNotNull(player.getMembership(), "Player should have the membership set");
		assertEquals(m, player.getMembership(), "Player should point to the new membership");

		assertTrue(clan.getMemberships().contains(m), "Clan should contain the new membership object");
	}

	@Test
	void testMembershipCreationViaClan_CreatesReverseConnection() {
		clan.addMembership(player);

		Membership m = player.getMembership();
		assertNotNull(m, "Membership should be created by the Clan");

		assertEquals(clan, m.getClan());
		assertEquals(player, m.getPlayer());
		assertTrue(clan.getMemberships().contains(m));
	}

	@Test
	void testRemoveMembership_BreaksConnections() {
		Membership m = new Membership(ClanRole.MEMBER, LocalDate.now(), clan, player);

		assertTrue(clan.getMemberships().contains(m));

		clan.removeMembership(m);

		assertFalse(clan.getMemberships().contains(m));


	}
}
