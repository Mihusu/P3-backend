package com.skarp.prio;

import com.skarp.prio.products.Category;
import com.skarp.prio.products.Product;
import com.skarp.prio.products.ProductState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    Product iphone;

    @BeforeEach
    public void setup() {
       iphone = new Product("somenumber","Apple", Category.IPHONE, "11 Pro", "256gb white", 4000, 1500);
    }

    @Test
    public void testConstructor() {
        assertNotNull(iphone);
        assertEquals(ProductState.DEFECTIVE, iphone.getState());
    }

    @Test
    public void testGetName() {
        assertEquals("Apple IPHONE 11 Pro 256gb white", iphone.getName());
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
    public void testStorageTime(){
        Product Lenovo = new Product("somenumber","Lenovo", Category.LAPTOP, "TI", "8gb RAM", 3000, 500);
        Lenovo.getStorageTime();
        assertEquals(0, Lenovo.getStorageTime());
    }


}



//product(ID, price, name) <- spare parts: spare parts (Price, ID, Origin):
// Origin<- linked list to an order list or a written-of product.
