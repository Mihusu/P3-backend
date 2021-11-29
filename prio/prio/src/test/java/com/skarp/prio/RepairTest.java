package com.skarp.prio;
import com.skarp.prio.products.Category;
import com.skarp.prio.products.Product;
import com.skarp.prio.repairs.Repair;

import com.skarp.prio.repairs.RepairState;
import com.skarp.prio.spareparts.Enums.Grade;
import com.skarp.prio.spareparts.Enums.SparePartType;
import com.skarp.prio.spareparts.NewSparePart;
import com.skarp.prio.spareparts.SparePart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RepairTest {
    Product iphone;

    @BeforeEach
    public void setup() {
        iphone = new Product("somenumber","Apple", Category.IPHONE, "11 Pro", "2018", "256gb white", 4000, 1500);
    }

    @Test
    public void canStartRepair() {

        Repair repair = new Repair(iphone);

        assertEquals(RepairState.ON_GOING, repair.getState());
    }


    @Test
    public void hasDate() {

        Repair repair = new Repair(iphone);

        assertNotNull(repair.getStartDate());
    }

    @Test
    public void canAddSparePart() {

        SparePart battery = new NewSparePart("Apple",Category.IPHONE,"11 Pro", "2019", Grade.A, SparePartType.BATTERY, 250);
        Repair repair = new Repair(iphone);

        repair.addSparePart(battery);

        assertEquals(1, repair.getAddedSpareParts().size());
    }


}
