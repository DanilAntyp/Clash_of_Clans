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
	void testRemoveMembership_BreaksConnections() {
		Membership m = new Membership(ClanRole.MEMBER, LocalDate.now(), clan, player);

		assertTrue(clan.getMemberships().contains(m));

		clan.removeMembership(m);

		assertFalse(clan.getMemberships().contains(m));


	}
}
