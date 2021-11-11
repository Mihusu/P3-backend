package com.skarp.prio;

import com.skarp.prio.products.Product;
import com.skarp.prio.spareparts.SparePart;
import com.skarp.prio.spareparts.UsedSparePart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    Product iphone;
    @BeforeEach
    public void setup() {
       iphone = new Product("Apple", "iPhone", "11 Pro", "2018", "256gb white", 4000, 1500);
    }

    @Test
    public void testConstructor() {
        assertNotNull(iphone);
    }

    @Test
    public void testGetName() {
        assertEquals("Iphone 12", iphone.getProductName());
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

        Product phone = new Product("Apple", "iPhone", "11 Pro", "2018", "256gb white", 4000, 2000);
        SparePart iphone_battery = new UsedSparePart(phone, "Battery", "OEM");

        assertTrue(iphone.addSparePart(iphone_battery));

    }

}



//product(ID, price, name) <- spare parts: spare parts (Price, ID, Origin):
// Origin<- linked list to an order list or a written-of product.
