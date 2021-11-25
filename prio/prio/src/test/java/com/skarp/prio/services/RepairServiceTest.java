package com.skarp.prio.services;

import com.skarp.prio.products.Category;
import com.skarp.prio.products.Product;
import com.skarp.prio.repairs.IllegalRepairOperationException;
import com.skarp.prio.repairs.Repair;
import com.skarp.prio.repairs.RepairService;
import com.skarp.prio.repairs.RepairState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class RepairServiceTest {

    Product testProduct;

    @Autowired
    RepairService repairService;

    @BeforeEach
    public void setup() {
        testProduct = new Product("Apple", Category.iphone, "11 Pro", "2018", "256gb white", 4000, 1500);
    }

    /*@Test
    public void WhenRepairAlreadyPaused_ShouldThrowException() {

        repairService.getRepairList(RepairState.PAUSED);
        Repair repair = new Repair(testProduct);

        repairService.pauseRepair(repair.getId());

        assertThrows(IllegalRepairOperationException.class, () -> repairService.pauseRepair(repair.getId()));
    }*/
}
