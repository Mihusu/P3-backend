package com.skarp.prio;
import com.skarp.prio.*;
import com.skarp.prio.products.Product;
import com.skarp.prio.repairs.Repair;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RepairTest {
    Product iphone;

    @BeforeEach
    public void setup() {
        iphone = new Product("Iphone 12", 4000, 1500);
    }


    @Test
    public void canStartRepair() {
        Product iphone = new Product("iPhone", 4000, 2000);

        Repair repair = new Repair(iphone);

        assertEquals("ON-GOING", repair.getState());
    }

    @Test
    public void canPauseRepair() {
        Product iphone = new Product("iPhone", 4000, 2000);

        Repair repair = new Repair(iphone);

        repair.pauseRepair();

        assertEquals("PAUSED", repair.getState());
    }

    @Test
    public void canResumeRepair() {
        Product iphone = new Product("iPhone", 4000, 2000);

        Repair repair = new Repair(iphone);

        repair.resumeRepair();

        assertEquals("ON-GOING", repair.getState());
    }

    @Test
    public void hasDate() {
        Product iphone = new Product("iPhone", 4000, 2000);

        Repair repair = new Repair(iphone);

        assertNotNull(repair.getStartDate());
    }


}
