package com.skarp.prio;

import com.skarp.prio.products.Category;
import com.skarp.prio.products.Product;
import com.skarp.prio.writeoffticket.WriteOffTicket;
import com.skarp.prio.writeoffticket.WriteOffTicketState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WriteOffTicketTest {

    Product iphone;
    Technician technician;

    @BeforeEach
    public void setup() {
        iphone = new Product("Apple", Category.IPHONE, "11 Pro", "2018", "256gb white", 4000, 1500);
        technician = new Technician("Sture", "iphone");
    }

    @Test
    public void canCreateWriteOffTicket() {

        WriteOffTicket myTestTicket = new WriteOffTicket(iphone, technician.getName());
        assertNotNull(myTestTicket);
    }

    @Test
    public void getCreatedDate() {
        WriteOffTicket myTestTicket = new WriteOffTicket(iphone, technician.getName());
        assertNotNull(myTestTicket.getCreationDate());
    }
    @Test
    public void approveWriteOffTicket(){
        WriteOffTicket myTestTicket = new WriteOffTicket(iphone, technician.getName());
        // Todo: reimplement this test when the decline method is working --- myTestTicket.approve();
        assertEquals(WriteOffTicketState.APPROVED, myTestTicket.getState());
    }
    @Test
    public void declineWriteOffTicket(){
        WriteOffTicket myTestTicket = new WriteOffTicket(iphone, technician.getName());
        // Todo: reimplement this test when the decline method is working --- myTestTicket.decline();
        assertEquals(WriteOffTicketState.DECLINED, myTestTicket.getState());
    }

    @Test
    public void canNotGetApprovalDateUntilApproved() {
        WriteOffTicket myTestTicket = new WriteOffTicket(iphone, technician.getName());
        assertNull(myTestTicket.getApprovalDate());
    }

    @Test
    public void canGetApprovalDate() {
        WriteOffTicket myTestTicket = new WriteOffTicket(iphone, technician.getName());
        // Todo: reimplement this test when the decline method is working --- myTestTicket.approve();
        assertNotNull(myTestTicket.getApprovalDate());
    }
}
