package com.skarp.prio;
import com.skarp.prio.products.Product;
import com.skarp.prio.repairs.Repair;

import com.skarp.prio.repairs.RepairState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RepairTest {
    Product iphone;

    @BeforeEach
    public void setup() {
        iphone = new Product("Apple", Category.iphone, "11 Pro", "2018", "256gb white", 4000, 1500);
    }

    @Test
    public void canStartRepair() {

        Repair repair = new Repair(iphone);

        assertEquals(RepairState.ON_GOING, repair.getState());
    }

    @Test
    public void canPauseRepair() {

        Repair repair = new Repair(iphone);

        repair.pauseRepair();

        assertEquals(RepairState.PAUSED, repair.getState());
    }

    @Test
    public void canResumeRepairAfterPause() {

        Repair repair = new Repair(iphone);

        repair.resumeRepair();

        assertEquals(RepairState.ON_GOING, repair.getState());
    }

    @Test
    public void hasDate() {

        Repair repair = new Repair(iphone);

        assertNotNull(repair.getStartDate());
    }


}
