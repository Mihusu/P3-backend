package com.skarp.prio.spareparts;

import com.skarp.prio.products.Category;
import com.skarp.prio.products.Product;
import com.skarp.prio.repairs.Repair;
import com.skarp.prio.spareparts.Enums.SparePartState;
import com.skarp.prio.spareparts.Enums.Grade;
import com.skarp.prio.spareparts.Enums.SparePartType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindsCompatibleSparePartsFromRepair() {
        Product testProduct = new Product("20005000","Apple", Category.IPHONE, "11 Pro", "256gb white", 4000, 1500);

        /* Create and save Used Spare part compatible with product */
        SparePart testSp = new UsedSparePart(testProduct.getProductId(), testProduct.getBrand(), testProduct.getCategory(), testProduct.getModel(), SparePartType.BATTERY, 200);
        testSp = spRepository.save(testSp);

        Repair testRepair = new Repair(testProduct);

        List<SparePart> spareParts = spService.getRecommendedSpareParts(testRepair);

        /* It is sufficient to test only that a single spare part is compatible with the product since they share the same query */
        assertEquals("Apple", spareParts.get(0).getBrand());
        assertEquals(Category.IPHONE, spareParts.get(0).getCategory());
        assertEquals("11 Pro", spareParts.get(0).getModel());

        /* Remove after test completion */
        spRepository.delete(testSp);
    }

    // Test if getSparePartList() returns a part matching query
    @Test
    void getSparePartList() {
        Product testProduct = new Product("20005000","Apple", Category.IPHONE, "11 Pro", "256gb white", 4000, 1500);

        SparePart testSp = new UsedSparePart(testProduct.getProductId(), testProduct.getBrand(), testProduct.getCategory(), testProduct.getModel(), SparePartType.BATTERY, 200);
        testSp.setState(SparePartState.AVAILABLE);
        testSp = spRepository.save(testSp);

        System.out.println(Category.IPHONE);
        // Perform query and compare the test part's stats with those of the first entry of the query's return object
        List<SparePart> testList = spService.getSparePartList(null,"Apple", Category.IPHONE.toString(), "11 Pro", SparePartType.BATTERY.toString(), SparePartState.AVAILABLE.toString(), "brand");
        assertFalse(testList.isEmpty());
        assertEquals(testSp.getBrand(), testList.get(0).getBrand());
        assertEquals(testSp.getCategory(), testList.get(0).getCategory());
        assertEquals(testSp.getModel(), testList.get(0).getModel());
        assertEquals(testSp.getType(), testList.get(0).getType());
        assertEquals(testSp.getState(), testList.get(0).getState());
        // assertEquals(testSp.getPart_id(), testList.get(testList.size()-1).getPart_id());

        /* Remove after test completion */
       spRepository.delete(testSp);
    }

    @Test
    void getSparePartByID() {
    }

    // make a new spare part
    @Test
    void TestUploadSparePart() {

        NewSparePart NewTestSp = spService.uploadSparePart("Apple","iPhone","11 Pro","Original","Screen",200.00,"200");

        assertEquals("APPLE", NewTestSp.getBrand());
        assertEquals(Category.IPHONE, NewTestSp.getCategory());
        assertEquals("11 PRO", NewTestSp.getModel());
        assertEquals(SparePartType.SCREEN, NewTestSp.getType());
        assertEquals(Grade.ORIGINAL, NewTestSp.getGrade());

        /* Remove after test completion */
        spRepository.delete(NewTestSp);
    }
}
