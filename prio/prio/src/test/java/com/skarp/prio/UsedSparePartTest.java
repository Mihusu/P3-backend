package com.skarp.prio;

import com.skarp.prio.products.Category;
import com.skarp.prio.products.Product;
import com.skarp.prio.spareparts.Enums.SparePartState;
import com.skarp.prio.spareparts.Enums.SparePartType;
import com.skarp.prio.spareparts.UsedSparePart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsedSparePartTest {

    Product iphone;
    @BeforeEach
    public void setup() {
        iphone = new Product("somenumber","Apple", Category.IPHONE, "11 Pro", "2018", "256gb white", 4000, 2000);
    }

    @Test
    public void hasGottenAProductOrigin() {

        UsedSparePart battery = new UsedSparePart(iphone, SparePartType.BATTERY, 200);
        assertNotNull(battery.getOriginProductId());
    }

    @Test
    public void isAvailableAfterSalvage() {
        UsedSparePart battery = new UsedSparePart(iphone, SparePartType.BATTERY, 200);

        assertEquals(SparePartState.AVAILABLE, battery.getState());
    }

}
// good job :)