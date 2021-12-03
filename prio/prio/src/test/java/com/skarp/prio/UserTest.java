package com.skarp.prio;

import com.skarp.prio.user.User;
import com.skarp.prio.user.UserPrivilege;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void demoteUser(){
        User Hans = new User("Hans","HH");
        User Frederik = new User("Frederik","FB");
        Hans.setUserPrivilege(UserPrivilege.FULL_ACCESS);
        Frederik.setUserPrivilege(UserPrivilege.SEMI_ACCESS);
        assertEquals(UserPrivilege.SEMI_ACCESS,Frederik.getUserPrivilege());
        Hans.demoteUser(Frederik);
        assertEquals(UserPrivilege.UNASSIGNED,Frederik.getUserPrivilege());
        assertEquals("Frederik", Frederik.getUsername());
        assertEquals("F", Frederik.getInitials());
        assertEquals("Frederik", Frederik.getUsername());
    }
    @Test
    public void checkCredentials(){
        User Hans = new User("Hans","HH");
        assertFalse(Hans.checkCredentials("Hans","hh"));
        assertFalse(Hans.checkCredentials("hans","HH"));
        assertFalse(Hans.checkCredentials("hans","hh"));
        assertTrue(Hans.checkCredentials("Hans","HH"));
    }
    @Test
    public void testInitials(){
        User Hans = new User("Hans Heje","HH");
        assertEquals("HH",Hans.getInitials());
    }
}
