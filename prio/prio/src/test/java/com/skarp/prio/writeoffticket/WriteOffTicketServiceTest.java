package com.skarp.prio.writeoffticket;

import com.skarp.prio.products.Category;
import com.skarp.prio.products.Product;
import com.skarp.prio.products.ProductRepository;
import com.skarp.prio.products.ProductState;
import com.skarp.prio.spareparts.Enums.SparePartState;
import com.skarp.prio.spareparts.SparePart;
import com.skarp.prio.spareparts.SparePartRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WriteOffTicketServiceTest {

  @Autowired
  WriteOffTicketRepository woRepository;

  @Autowired
  ProductRepository pRepository;

  @Autowired
  SparePartRepository spRepository;

  @Autowired
  WriteOffTicketService woService;

  Product testProduct;
  WriteOffTicketForm fakeForm;
  WriteOffTicket testTicket;

  @BeforeEach
  void setup() {
    testProduct = new Product("20004000", "Apple", Category.IPHONE, "11 Pro", "128 gb White", 4500, 2000);
    testProduct = pRepository.save(testProduct);

    fakeForm = new WriteOffTicketForm();
    fakeForm.setReason("Product under test");
    fakeForm.addMarkedParts(Arrays.asList("SCREEN", "BATTERY"));

  }

  @Test
  void sanityTest() {
    assertNotNull(woRepository);
    assertNotNull(pRepository);
    assertNotNull(spRepository);
    assertNotNull(woService);
  }

  @Test
  void testStatesWhenCreatingWriteoff() {

    testTicket = woService.createWriteOffTicket(fakeForm, testProduct.getId(), "Bertan");
    assertEquals(ProductState.IN_WRITEOFF, testTicket.getProduct().getState());
    assertEquals(WriteOffTicketState.AWAITING, testTicket.getState());
    assertEquals(SparePartState.MARKED_FUNCTIONAL, testTicket.getSpareParts().get(0).getState());
  }

  @Test
  void testStatesWhenApprovingWriteoff() {

    testTicket = woService.createWriteOffTicket(fakeForm, testProduct.getId(), "Bertan");

    woService.approveWriteOffTicket(testTicket.getId());

    //Retrieve part and product after WO approval from db to test states
    SparePart part = spRepository.findById(testTicket.getSpareParts().get(0).getPart_id()).get();
    Product product = pRepository.findById(testTicket.getProduct().getId()).get();

    assertEquals(ProductState.WRITTEN_OFF, product.getState());
    assertEquals(SparePartState.AVAILABLE, part.getState());
  }

  @Test
  void testStatesWhenDisapprovingWriteOff() {

    testTicket = woService.createWriteOffTicket(fakeForm, testProduct.getId(), "Bertan");

    woService.disApproveWriteOffTicket(testTicket.getId());

    //Find the updated product
    Product product = pRepository.findById(testTicket.getProduct().getId()).get();

    assertEquals(ProductState.DEFECTIVE, product.getState());

  }
}
