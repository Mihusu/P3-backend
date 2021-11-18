package com.skarp.prio;

import com.skarp.prio.products.Product;
import com.skarp.prio.user.User;
import com.skarp.prio.user.UserPrivilege;
import org.junit.jupiter.api.BeforeEach;
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
        assertEquals(UserPrivilege.VIEW_ONLY,Frederik.getUserPrivilege());
        assertEquals("Frederik", Frederik.getName());
        assertEquals("FB", Frederik.getInitials());
        assertEquals("Frederik", Frederik.getName());

    }
}
