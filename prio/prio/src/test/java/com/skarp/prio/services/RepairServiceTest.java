package com.skarp.prio.services;

import com.skarp.prio.products.Category;
import com.skarp.prio.products.Product;
import com.skarp.prio.repairs.RepairService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public class RepairServiceTest {

    Product testProduct;

    @Autowired
    RepairService repairService;

    @BeforeEach
    public void setup() {
        testProduct = new Product("Apple", Category.IPHONE, "11 Pro", "2018", "256gb white", 4000, 1500);
    }

    /*@Test
    public void WhenRepairAlreadyPaused_ShouldThrowException() {

        repairService.getRepairList(RepairState.PAUSED);
        Repair repair = new Repair(testProduct);

        repairService.pauseRepair(repair.getId());

        assertThrows(IllegalRepairOperationException.class, () -> repairService.pauseRepair(repair.getId()));
    }*/
}
