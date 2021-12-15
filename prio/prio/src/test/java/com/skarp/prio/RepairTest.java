package com.skarp.prio;
import com.skarp.prio.products.*;
import com.skarp.prio.repairs.*;

import com.skarp.prio.spareparts.Enums.Grade;
import com.skarp.prio.spareparts.Enums.SparePartState;
import com.skarp.prio.spareparts.Enums.SparePartType;
import com.skarp.prio.spareparts.NewSparePart;
import com.skarp.prio.spareparts.SparePart;
import com.skarp.prio.spareparts.SparePartRepository;
import com.skarp.prio.user.User;
import com.skarp.prio.user.UserPrivilege;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RepairTest {
    Product iphone = new Product("somenumber","Apple", Category.IPHONE, "11 Pro", "256gb white", 4000, 1500);;

    @Autowired
    private RepairRepository repairRepository;

    @Autowired
    private SparePartRepository sparePartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RepairServiceImpl repairService;

    @Test
    void sanityTest() {
        assertNotNull(repairService);
        assertNotNull(repairRepository);
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
    public void whenRepairIsCreated_ProductIsUnderRepair() {
        // Making a user and set its privilege
        User user = new User("Somename", "password");
        user.setUserPrivilege(UserPrivilege.SEMI_ACCESS);

        //Get back the saved product to retrieve ID
        Product savedProduct = productRepository.save(iphone);

        // Service under test for creating repair
        Repair updateRepair = repairService.createRepair(savedProduct.getId(), user.getUsername());

        assertEquals(ProductState.IN_REPAIR, updateRepair.getProduct().getState());
    }

    @Test
    public void whenPauseRepair_ExpectRepairToBePaused() {

        Repair repair = new Repair(iphone);

        //Get back the saved repair to retrieve ID
        Repair savedRepair = repairRepository.save(repair);

        //Service under test
        Repair updatedRepair = repairService.pauseRepair(savedRepair.getId());

        //The repair is now PAUSED
        assertEquals(RepairState.PAUSED, updatedRepair.getState());

    }

    @Test
    public void whenFinisingAPausedRepair_throwIllegalRepairOperationException() {
        Repair repair = new Repair(iphone);

        //Get back the saved repair to retrieve ID
        Repair savedRepair = repairRepository.save(repair);

        //Service under test for repair
        repairService.finishRepair(savedRepair.getId());

        //The repair cannot be paused when it is finished
        assertThrows(IllegalRepairOperationException.class,
                () -> repairService.pauseRepair(savedRepair.getId()));
    }

    @Test
    public void whenGivenUnknownRepairID_throwsNoSuchElementException() {
        String fakeRepairID = "123456789";

        //The repair cannot be paused when it is finished
        assertThrows(NoSuchElementException.class,
                () -> repairService.pauseRepair(fakeRepairID));

    }

    @Test
    public void whenResumeRepair_ExpectRepairToBeResumed() {

        Repair repair = new Repair(iphone);

        //Get back the saved repair to retreive ID
        Repair savedRepair = repairRepository.save(repair);

        // Repair is now PAUSED
        repairService.pauseRepair(savedRepair.getId());

        //Service under test for repair
        Repair updatedRepairToResume = repairService.resumeRepair(savedRepair.getId());

        //The repair is now RESUMED
        assertEquals(RepairState.ON_GOING, updatedRepairToResume.getState());

    }

    @Test
    public void whenSparePartIsAddedToRepair_SparePartIsReserved() {
        SparePart battery = new NewSparePart("Apple",Category.IPHONE,"11 Pro", Grade.A, SparePartType.BATTERY, 250, "23124124");
        Repair repair = new Repair(iphone);

        //Get back the saved repair to retrieve ID
        Repair savedRepair = repairRepository.save(repair);
        SparePart savedSP = sparePartRepository.save(battery);

        //Service under test for adding spare part
        SparePart updatedPart = repairService.addSparePart(savedRepair.getId(), savedSP.getPart_id());

        assertEquals(SparePartState.RESERVED, updatedPart.getState());
    }

    @Test
    public void whenRepairIsCancelled_SparePartsIsAvailable() {
        SparePart battery = new NewSparePart("Apple",Category.IPHONE,"11 Pro", Grade.A, SparePartType.BATTERY, 250, "23124124");
        Repair repair = new Repair(iphone);

        //Get back the saved repair to retrieve ID
        Repair savedRepair = repairRepository.save(repair);
        SparePart savedSP = sparePartRepository.save(battery);

        //Service under test for repair
        SparePart updatePart = repairService.removeSparePart(savedRepair.getId(), savedSP.getPart_id());

        assertEquals(SparePartState.AVAILABLE, updatePart.getState());

    }

    @Test
    public void whenRepairIsCancelled_ProductIsDefective() {
        Repair repair = new Repair(iphone);

        //Get back the saved repair to retrieve ID
        //Get back the saved product to retrieve ID
        Repair savedRepair = repairRepository.save(repair);
        Product savedProduct = productRepository.save(iphone);

        //Service under test for repair
        repairService.cancelRepair(savedRepair.getId());

        // Get the product state
        assertEquals(ProductState.DEFECTIVE, savedProduct.getState());
    }

    @Test
    public void whenFinishingARepair_ExpectRepairToBeFinished() {
        Repair repair = new Repair(iphone);

        //Get back the saved repair to retrieve ID
        Repair savedRepair = repairRepository.save(repair);

        // Testing the repair service for finish repair
        repairService.finishRepair(savedRepair.getId());

        // Finding the saved repair in the database again to get the specific repair as well
        Repair updatedRepair = repairRepository.findById(savedRepair.getId()).get();

        //The repair cannot be paused when it is finished
        assertEquals(RepairState.FINISHED, updatedRepair.getState());
    }

    @Test
    public void whenARepairIsFinished_ExpectProductToBeRepaired() {
        Repair repair = new Repair(iphone);

        //Get back the saved repair to retrieve ID
        Repair savedRepair = repairRepository.save(repair);
        Product savedProduct = productRepository.save(iphone);

        // Testing the repair service for finish repair
        repairService.finishRepair(savedRepair.getId());

        // Finding the saved repair in the database again to get the specific product as well
        Repair updatedRepair = repairRepository.findById(savedRepair.getId()).get();

        //The repair cannot be paused when it is finished
        assertEquals(ProductState.REPAIRED, updatedRepair.getProduct().getState());
    }

    @Test
    public void whenARepairIsFinished_ExpectSparePartToBeConsumed() {
        SparePart battery = new NewSparePart("Apple",Category.IPHONE,"11 Pro", Grade.A, SparePartType.BATTERY, 250, "23124124");
        battery.setState(SparePartState.CONSUMED);
        Repair repair = new Repair(iphone);

        //Get back the saved repair to retrieve ID
        Repair savedRepair = repairRepository.save(repair);
        SparePart savedSP = sparePartRepository.save(battery);

        //Service under test for adding spare part
        repairService.finishRepair(savedRepair.getId());

        //Service under test for update spare part
        SparePart updateSparePart = sparePartRepository.findById(savedSP.getPart_id()).get();

        assertEquals(SparePartState.CONSUMED, updateSparePart.getState());
    }

    @Test
    public void whenARepairIsFinished_ExpectProductCostPriceToBeUpdated() {
        SparePart battery = new NewSparePart("Apple",Category.IPHONE,"11 Pro", Grade.A, SparePartType.BATTERY, 250, "23124124");
        Repair repair = new Repair(iphone);

        repair.addSparePart(battery);

        // Get back the saved repair to retrieve ID
        Repair savedRepair = repairRepository.save(repair);
        Product savedProduct = productRepository.save(iphone);
        SparePart savedSparePart = sparePartRepository.save(battery);

        // Testing the repair service for finish repair
        repairService.finishRepair(savedRepair.getId());

        // Finding the respective IDs of repair and product in the database
        Repair updatedRepair = repairRepository.findById(savedRepair.getId()).get();
        Product updatedProduct = productRepository.save(savedProduct);
        Product updatedProductPrice = productRepository.findById(updatedProduct.getId()).get();

        // When repair finished then it will get the cost price of the product
        assertEquals(updatedRepair.getProduct().getCostPrice(), updatedProductPrice.getCostPrice() + savedSparePart.getCostPrice());
    }

    /**
     * Skal måske slettes siden funktionen bliver også testet i whenRepairIsCancelled_SparePartsIsAvailable.
     */
    @Test
    public void whenSparePartIsRemoved_SparePartsIsAvailable() {
        SparePart battery = new NewSparePart("Apple",Category.IPHONE,"11 Pro", Grade.A, SparePartType.BATTERY, 250, "23124124");
        Repair repair = new Repair(iphone);

        //Get back the saved spare part to retrieve ID
        Repair savedRepair = repairRepository.save(repair);
        SparePart savedSP = sparePartRepository.save(battery);

        //Service under test for repair
        SparePart updatePart = repairService.removeSparePart(savedRepair.getId(), savedSP.getPart_id());

        assertEquals(SparePartState.AVAILABLE, updatePart.getState());

    }
}
