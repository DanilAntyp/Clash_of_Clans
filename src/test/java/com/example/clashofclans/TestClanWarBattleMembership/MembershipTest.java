package com.example.clashofclans.TestClanWarBattleMembership;

import com.example.clashofclans.Membership;
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

        Membership m = new Membership(ClanRole.ELDER, join);

        assertEquals(ClanRole.ELDER, m.getClanRole());
        assertEquals(join, m.getJoinDate());
        assertNull(m.getDateEnd());
        assertFalse(m.getIsBanned());
    }

    @Test
    void testNullClanRoleThrowsException() {
        LocalDate join = LocalDate.now();

        assertThrows(InvalidClanRoleException.class,
                () -> new Membership(null, join));
    }

    @Test
    void testNullJoinDateThrowsException() {
        assertThrows(InvalidJoinDateException.class,
                () -> new Membership(ClanRole.LEADER, null));
    }

    @Test
    void testEndDateBeforeJoinThrowsException() {
        LocalDate join = LocalDate.of(2024, 5, 10);
        LocalDate end = LocalDate.of(2024, 5, 1);

        Membership m = new Membership(ClanRole.MEMBER, join);

        assertThrows(InvalidEndDateException.class,
                () -> m.setDateEnd(end));
    }

    @Test
    void testExtentStoresObjects() {
        LocalDate d = LocalDate.now();

        Membership m1 = new Membership(ClanRole.ELDER, d);
        Membership m2 = new Membership(ClanRole.MEMBER, d);

        List<Membership> extent = Membership.getExtent();

        assertEquals(2, extent.size());
        assertTrue(extent.contains(m1));
        assertTrue(extent.contains(m2));
    }

    @Test
    void testExtentIsUnmodifiable() {
        Membership m = new Membership(ClanRole.LEADER, LocalDate.now());

        List<Membership> extent = Membership.getExtent();

        assertThrows(UnsupportedOperationException.class, extent::clear);
    }

    @Test
    void testJoinMethod() {
        Membership m = new Membership(ClanRole.MEMBER, LocalDate.of(2022, 1, 1));
        m.setDateEnd(LocalDate.of(2023, 1, 1));
        m.setIsBanned(true);

        m.Join();

        assertEquals(LocalDate.now(), m.getJoinDate());
        assertNull(m.getDateEnd());
        assertFalse(m.getIsBanned());
    }

    @Test
    void testLeaveMethod() {
        Membership m = new Membership(ClanRole.ELDER, LocalDate.now());

        m.Leave();

        assertEquals(LocalDate.now(), m.getDateEnd());
    }

    @Test
    void testDateEndSetterValid() {
        LocalDate join = LocalDate.of(2024, 5, 10);
        LocalDate end = LocalDate.of(2024, 5, 15);

        Membership m = new Membership(ClanRole.MEMBER, join);
        m.setDateEnd(end);

        assertEquals(end, m.getDateEnd());
    }
}