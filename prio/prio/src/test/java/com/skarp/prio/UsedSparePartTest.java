package com.skarp.prio;

import com.skarp.prio.products.Category;
import com.skarp.prio.products.Product;
import com.skarp.prio.spareparts.Enums.SparePartType;
import com.skarp.prio.spareparts.UsedSparePart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsedSparePartTest {

    Product iphone;
    @BeforeEach
    public void setup() {
        iphone = new Product("somenumber","Apple", Category.IPHONE, "11 Pro", "256gb white", 4000, 2000);
    }

    @Test
    public void hasGottenAProductOrigin() {

        UsedSparePart battery = new UsedSparePart(iphone.getProductId(), iphone.getBrand(), iphone.getCategory(),iphone.getModel(), SparePartType.BATTERY, 200);
        assertNotNull(battery.getOriginProductId());
    }

    @Test
    public void test() {

    }


}
// good job :)