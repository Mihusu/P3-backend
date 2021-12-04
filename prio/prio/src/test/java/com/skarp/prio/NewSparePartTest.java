package com.skarp.prio;

import com.skarp.prio.products.Category;
import com.skarp.prio.spareparts.Enums.Grade;
import com.skarp.prio.spareparts.Enums.SparePartType;
import com.skarp.prio.spareparts.NewSparePart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NewSparePartTest {

    NewSparePart battery;

    @BeforeEach
    public void setup() {
        battery = new NewSparePart(
                "Apple", Category.IPHONE, "11 Pro",
                Grade.OEM, SparePartType.BATTERY, 260, "123122");
    }

    @Test
    void getLocation() {
        battery.setLocation("J6");

        assertEquals("J6", battery.getLocation());
    }

    @Test
    void getSku() {

        assertEquals("123122",battery.getSku());
    }

    @Test
    void setLocation() {
        battery.setLocation("H7");

        assertEquals("H7",battery.getLocation());
    }

    @Test
    void setSku() {
        battery.setSku("iP11parb");

        assertEquals("iP11parb",battery.getSku());
    }

    @Test
    void setGrade() {
        battery.setGrade(Grade.ORIGINAL);

        assertEquals(Grade.ORIGINAL, battery.getGrade());
    }
}