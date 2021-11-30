package com.skarp.prio.writeoffticket;

import com.skarp.prio.products.Product;
import com.skarp.prio.products.ProductRepository;
import com.skarp.prio.products.ProductState;
import com.skarp.prio.spareparts.Enums.SparePartState;
import com.skarp.prio.spareparts.Enums.SparePartType;
import com.skarp.prio.spareparts.SparePartRepository;
import com.skarp.prio.spareparts.UsedSparePart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class WriteOffTicketServiceImpl implements WriteOffTicketService{

    @Autowired
    WriteOffTicketRepository writeOffTicketRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    SparePartRepository sparePartRepository;

    @Override
    public void createWriteOffTicket(@RequestBody WriteOffTicketForm woForm, String prod_id, String tech_id) throws Exception {
        System.out.println("about to findById("+prod_id+")");
        // Optional<Product> DBproduct = productRepository.findById(prod_id); // Todo: this seems to make error when product has spare parts in it's array, consider storing spare part id instead of actual object

        Product product = productRepository.findById(prod_id).orElseThrow();

        System.out.println("checking for empty product");
      //  if (DBproduct.isEmpty())
      //      throw new NoSuchElementException("Item not found in database");

      //  Product product = DBproduct.get();
        System.out.println("checking for in write-off state");
        if (product.getState() == ProductState.IN_WRITEOFF)
            throw new Exception("Product is already in write-off");

        System.out.println("Parsing");
        List<SparePartType> sparePartTypes = WriteOffFormParser.parseTypes(woForm);
        System.out.println("Type" + sparePartTypes.get(0));
        System.out.println("Parsing of types successful: " + sparePartTypes);
        System.out.println(sparePartTypes.size());
        /*Generate used spare-parts from the product under write-off*/
        for (SparePartType type : sparePartTypes) {

            UsedSparePart part = new UsedSparePart(product, type, product.getCostPrice() / (double) sparePartTypes.size());
            part.setState(SparePartState.MARKED_FUNCTIONAL);
            System.out.println(part.getState() + "<- state --- type -> " + part.getType());
            product.addSparePart(part);
            System.out.println("added part");
            sparePartRepository.save(part);
            System.out.println("saved part");

        }

        WriteOffTicket ticket = new WriteOffTicket(product, tech_id);
        ticket.addReason(woForm.reason);

        productRepository.save(product);
        writeOffTicketRepository.save(ticket);


    }

    // Todo: make get mappings for declining and approving write off tickets
    @Override
    public List<WriteOffTicket> getAllWriteOffTickets() {
        return null;
    }

    @Override
    public void  approveWriteOffTicket() {

    }

    @Override
    public void declineWriteOffTicket() {

    }
}
