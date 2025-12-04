package com.example.clashofclans.TestClanWarBattleMembership;

import com.example.clashofclans.clanRelated.Clan;
import com.example.clashofclans.clanRelated.Membership;
import com.example.clashofclans.theRest.Player;
import com.example.clashofclans.enums.ClanRole;
import com.example.clashofclans.exceptions.membership.InvalidClanRoleException;
import com.example.clashofclans.exceptions.membership.InvalidEndDateException;
import com.example.clashofclans.exceptions.membership.InvalidJoinDateException;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MembershipTest {

    @BeforeEach
    void resetExtent() throws Exception {
        Field extentField = Membership.class.getDeclaredField("extent");
        extentField.setAccessible(true);
        ((List<?>) extentField.get(null)).clear();
    }

    @Test
    void testValidMembershipCreation() {
        LocalDate join = LocalDate.of(2024, 5, 10);

        Player player = new Player("John Doe");
        Clan clan = new Clan("name" ,"description");


        Membership m = new Membership(ClanRole.ELDER, join, clan, player);

        assertEquals(ClanRole.ELDER, m.getClanRole());
        assertEquals(join, m.getJoinDate());
        assertNull(m.getDateEnd());
        assertFalse(m.getIsBanned());
    }

    @Test
    void testNullClanRoleThrowsException() {
        Player player = new Player("John Doe");
        Clan clan = new Clan("name" ,"description");
        LocalDate join = LocalDate.now();

        assertThrows(InvalidClanRoleException.class,
                () -> new Membership(null, join , clan, player));
    }

    @Test
    void testNullJoinDateThrowsException() {
        Player player = new Player("John Doe");
        Clan clan = new Clan("name" ,"description");
        assertThrows(InvalidJoinDateException.class,
                () -> new Membership(ClanRole.LEADER, null , clan, player));
    }

    @Test
    void testEndDateBeforeJoinThrowsException() {
        Player player = new Player("John Doe");
        Clan clan = new Clan("name" ,"description");
        LocalDate join = LocalDate.of(2024, 5, 10);
        LocalDate end = LocalDate.of(2024, 5, 1);

        Membership m = new Membership(ClanRole.MEMBER, join , clan, player);

        assertThrows(InvalidEndDateException.class,
                () -> m.setDateEnd(end));
    }

    @Test
    void testExtentStoresObjects() {
        Player player = new Player("John Doe");
        Clan clan = new Clan("name" ,"description");
        Player player1 = new Player("John Doe1");
        Clan clan1 = new Clan("name" ,"description1");
        LocalDate d = LocalDate.now();

        Membership m1 = new Membership(ClanRole.ELDER, d , clan, player);
        Membership m2 = new Membership(ClanRole.MEMBER, d , clan1, player1);

        List<Membership> extent = Membership.getExtent();

        assertEquals(2, extent.size());
        assertTrue(extent.contains(m1));
        assertTrue(extent.contains(m2));
    }

    @Test
    void testExtentIsUnmodifiable() {
        Player player = new Player("John Doe");
        Clan clan = new Clan("name" ,"description");
        Membership m = new Membership(ClanRole.LEADER, LocalDate.now() , clan, player);

        List<Membership> extent = Membership.getExtent();

        assertThrows(UnsupportedOperationException.class, extent::clear);
    }

    @Test
    void testJoinMethod() {
        Player player = new Player("John Doe");
        Clan clan = new Clan("name" ,"description");
        Membership m = new Membership(ClanRole.MEMBER, LocalDate.of(2022, 1, 1) , clan, player);
        m.setDateEnd(LocalDate.of(2023, 1, 1));
        m.setIsBanned(true);

        m.Join();

        assertEquals(LocalDate.now(), m.getJoinDate());
        assertNull(m.getDateEnd());
        assertFalse(m.getIsBanned());
    }

    @Test
    void testLeaveMethod() {
        Player player = new Player("John Doe");
        Clan clan = new Clan("name" ,"description");
        Membership m = new Membership(ClanRole.ELDER, LocalDate.now() , clan, player);

        m.Leave();

        assertEquals(LocalDate.now(), m.getDateEnd());
    }

    @Test
    void testDateEndSetterValid() {
        Player player = new Player("John Doe");
        Clan clan = new Clan("name" ,"description");
        LocalDate join = LocalDate.of(2024, 5, 10);
        LocalDate end = LocalDate.of(2024, 5, 15);

        Membership m = new Membership(ClanRole.MEMBER, join , clan, player);
        m.setDateEnd(end);

        assertEquals(end, m.getDateEnd());
    }
}