package com.skarp.prio.writeoffticket;

import com.skarp.prio.products.Category;
import com.skarp.prio.products.Product;
import com.skarp.prio.products.ProductRepository;
import com.skarp.prio.products.ProductState;
import com.skarp.prio.spareparts.Enums.SparePartState;
import com.skarp.prio.spareparts.Enums.SparePartType;
import com.skarp.prio.spareparts.SparePartRepository;
import com.skarp.prio.spareparts.UsedSparePart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.DateOperators;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class WriteOffTicketServiceImpl implements WriteOffTicketService{

    @Autowired
    MongoOperations operations;
    @Autowired
    WriteOffTicketRepository writeOffTicketRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    SparePartRepository sparePartRepository;

    @Override
    public void createWriteOffTicket(@RequestBody WriteOffTicketForm woForm, String prod_id, String tech_id) throws Exception {
        System.out.println("about to findById("+prod_id+")");
        Optional<Product> DBproduct = productRepository.findById(prod_id);

        if (DBproduct.isEmpty())
            throw new NoSuchElementException("Item not found in database");

        Product product = DBproduct.get();

        if (product.getState() == ProductState.IN_WRITEOFF)
            throw new Exception("Product is already in write-off");

        List<SparePartType> sparePartTypes = WriteOffFormParser.parseTypes(woForm);

        /*Generate used spare-parts from the product under write-off*/
        for (SparePartType type : sparePartTypes) {
            UsedSparePart part = new UsedSparePart(product.getProductId(), product.getBrand(), product.getCategory(), product.getModel(), product.getYear(), type,product.getCostPrice() / (double) sparePartTypes.size());
            part.setState(SparePartState.MARKED_FUNCTIONAL);

            product.addSparePart(part);

            sparePartRepository.save(part);

        }

        WriteOffTicket ticket = new WriteOffTicket(product, tech_id);
        ticket.addReason(woForm.getReason());
        productRepository.save(product);
        writeOffTicketRepository.save(ticket);

    }

    // Todo: make get mappings for declining and approving write off tickets
    @Override
    public List<WriteOffTicket> getAllWriteOffTickets() {
        return operations.findAll(WriteOffTicket.class);
    }

    @Override
    public void  approveWriteOffTicket() {

    }

    @Override
    public void declineWriteOffTicket() {

    }
}
