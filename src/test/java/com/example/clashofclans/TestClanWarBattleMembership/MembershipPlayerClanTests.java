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

		clan =new Clan("Test" , "Desc");
		player = new Player ("Danylo");
	}

	@Test
	void testMembershipCreationViaConstructor_CreatesReverseConnection() {
		Membership m = new Membership(ClanRole.MEMBER, LocalDate.now(), clan, player);
		assertEquals(m, player.getMembership(), "The Membership object should be set on the Player object during creation.");

		assertEquals(clan, m.getClan(), "The Membership object should correctly reference the Clan.");
		assertEquals(player, m.getPlayer(), "The Membership object should correctly reference the Player.");

		assertTrue(clan.getMemberships().contains(m), "The Clan's list of memberships should contain the newly created Membership object.");
	}

	@Test
	void testRemoveMembership_BreaksConnections() {
		Membership m = new Membership(ClanRole.MEMBER, LocalDate.now(), clan, player);

		assertTrue(clan.getMemberships().contains(m));

		clan.removeMembership(m);

		assertFalse(clan.getMemberships().contains(m));


	}
	@Test
	void testMembershipLeave_SetsEndDate() {
		LocalDate joinDate = LocalDate.of(2025, 1, 1);
		Membership m = new Membership(ClanRole.MEMBER, joinDate, clan, player);

		assertNull(m.getDateEnd(), "End date should be null initially.");
		assertEquals(joinDate, m.getJoinDate(), "Join date should be correct.");

		m.Leave();

		assertEquals(LocalDate.now(), m.getDateEnd(), "End date should be set to the current date when Leave() is called.");

		assertEquals(clan, m.getClan(), "Clan connection should remain.");
		assertEquals(m, player.getMembership(), "Player connection should remain.");
	}
	@Test
	void testAddMembership_BannedPlayerThrowsException() {
		clan.addBan(player);

		assertNull(player.getMembership(), "Player should have no membership after being banned.");

		assertThrows(com.example.clashofclans.exceptions.clan.memberAddingExeption.class, () -> {
			clan.addMembership(player);
		}, "Attempting to add a banned player should throw a memberAddingExeption.");

		assertNull(player.getMembership(), "Membership should not be created after failed attempt.");
		assertTrue(clan.getBanList().contains(player), "Player should remain on the ban list.");
	}
}
