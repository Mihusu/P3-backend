package com.skarp.prio;

import com.skarp.prio.products.Category;
import com.skarp.prio.products.Product;
import com.skarp.prio.products.ProductState;
import com.skarp.prio.repairs.Repair;
import com.skarp.prio.spareparts.Enums.Grade;
import com.skarp.prio.spareparts.SparePart;
import com.skarp.prio.spareparts.UsedSparePart;
import com.skarp.prio.writeoffticket.WriteOffTicket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    Product iphone;

    @BeforeEach
    public void setup() {
       iphone = new Product("Apple", Category.IPHONE, "11 Pro", "2018", "256gb white", 4000, 1500);
    }

    @Test
    public void testConstructor() {
        assertNotNull(iphone);
        assertEquals(ProductState.DEFECTIVE, iphone.getState());
    }

    @Test
    public void testGetName() {
        assertEquals("Apple IPHONE 11 Pro 2018 256gb white", iphone.getName());
    }

    @Test
    public void testGetSalesPrice() {
        assertEquals(4000, iphone.getSalesPrice());
    }

    @Test
    public void testGetCostPrice() {
        assertEquals(1500, iphone.getCostPrice());
    }

    @Test public void testSetCostPrice() {
        iphone.setCostPrice(2000);
        assertEquals(2000, iphone.getCostPrice());
        assertFalse(iphone.setCostPrice(-2000));
    }

    @Test public void testCanNotSetNegCostPrice() {
        assertFalse(iphone.setCostPrice(-2000));
    }

    @Test
    public void testGetProfitSum(){
        assertEquals(2500, iphone.getProfitSum());
    }

    @Test
    public void testGetProfitMargin(){
        assertEquals(62.5, iphone.getProfitMargin());
    }

    @Test
    public void testCanAddSparePart() {

        Product phone = new Product("Apple", Category.IPHONE, "11 Pro", "2018", "256gb white", 4000, 2000);
        SparePart iphone_battery = new UsedSparePart(phone, Grade.A, "Battery", 200);

        assertTrue(iphone.addSparePart(iphone_battery));

    }

    @Test
    public void testStateAfterWriteOff(){
        Technician technician = new Technician("Jakob", "MacBook");
        WriteOffTicket testTicket = new WriteOffTicket(iphone, technician.getName());

        assertEquals(ProductState.IN_WRITEOFF, iphone.getState());
    }

    @Test
    public void testStateAfterWriteOffDecline() {
        Technician technician = new Technician("Jakob", "MacBook");
        WriteOffTicket testTicket = new WriteOffTicket(iphone, technician.getName());

        // Todo: reimplement this test when the decline method is working --- testTicket.decline();

        assertEquals(ProductState.DEFECTIVE, iphone.getState());
    }

    @Test
    public void testStateAfterWriteOffApproved() {
        Technician technician = new Technician("Jakob", "MacBook");
        WriteOffTicket testTicket = new WriteOffTicket(iphone, technician.getName());

        // Todo: reimplement this test when the decline method is working --- testTicket.approve();

        assertEquals(ProductState.WRITTEN_OFF, iphone.getState());
    }

    @Test
    public void testStateAfterRepairStarted() {

        Repair iphone_repair = new Repair(iphone);

        assertEquals(ProductState.IN_REPAIR, iphone.getState());
    }

    @Test
    public void testStorageTime(){
        Product Lenovo = new Product("Lenovo", Category.LAPTOP, "TI", "2016", "8gb RAM", 3000, 500);
        Lenovo.getStorageTime();
        assertEquals(0, Lenovo.getStorageTime());
    }


}



//product(ID, price, name) <- spare parts: spare parts (Price, ID, Origin):
// Origin<- linked list to an order list or a written-of product.
