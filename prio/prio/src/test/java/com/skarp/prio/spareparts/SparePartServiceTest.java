package com.skarp.prio.spareparts;

import com.skarp.prio.products.Category;
import com.skarp.prio.products.Product;
import com.skarp.prio.repairs.Repair;
import com.skarp.prio.spareparts.Enums.SparePartType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@SpringBootTest
public class SparePartServiceTest {

    @Autowired
    SparePartRepository spRepository;

    @Autowired
    SparePartService spService;

    @Test
    void testFindsCompatibleSparePartsFromRepair() {

        Product testProduct = new Product("20005000","Apple", Category.IPHONE, "11 Pro", "2018", "256gb white", 4000, 1500);

        /* Create and save Used Sparepart from product that should be found */
        SparePart testSp = new UsedSparePart(testProduct.getProductId(), testProduct.getBrand(), testProduct.getCategory(), testProduct.getModel(), testProduct.getYear(), SparePartType.BATTERY, 200);
        testSp = spRepository.save(testSp);

        Repair testRepair = new Repair(testProduct);

        List<SparePart> spareParts = spService.getRecommendedSpareParts(testRepair);

        /* It is sufficient to test only that a single sparepart is compatible with the product since they share the same queury*/
        assertEquals("Apple", spareParts.get(0).getBrand());
        assertEquals(Category.IPHONE, spareParts.get(0).getCategory());
        assertEquals("11 Pro", spareParts.get(0).getModel());

        /* Remove after test completion */
        spRepository.delete(testSp);
    }
}
