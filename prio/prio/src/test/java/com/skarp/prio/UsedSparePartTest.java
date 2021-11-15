package com.skarp.prio;

import com.skarp.prio.products.Product;
import com.skarp.prio.spareparts.Enums.UsedSparePartState;
import com.skarp.prio.spareparts.UsedSparePart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsedSparePartTest {

    Product iphone;
    @BeforeEach
    public void setup() {
        iphone = new Product("Apple", "iPhone", "11 Pro", "2018", "256gb white", 4000, 2000);
    }

    @Test
    public void hasGottenAProductOrigin() {

        UsedSparePart battery = new UsedSparePart(iphone, "OEM", "Battery");
        assertNotNull(battery.getOrigin());
    }

    @Test
    public void isAvailableAfterSalvage() {
        UsedSparePart battery = new UsedSparePart(iphone, "OEM", "Battery");

        assertEquals(UsedSparePartState.AVAILABLE, battery.getState());
    }

}
// good job :)