package com.skarp.prio.writeoffticket;

import com.skarp.prio.products.Category;
import com.skarp.prio.products.Product;
import com.skarp.prio.products.ProductRepository;
import com.skarp.prio.products.ProductState;
import com.skarp.prio.spareparts.Enums.SparePartState;
import com.skarp.prio.spareparts.Enums.SparePartType;
import com.skarp.prio.spareparts.SparePart;
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

        if (product.getState() != ProductState.IN_REPAIR && product.getState() != ProductState.DEFECTIVE)
            throw new Exception("Product state not applicable for write-off");

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
    public void approveWriteOffTicket(String id) throws Exception {
        Optional<WriteOffTicket> DBticket = writeOffTicketRepository.findById(id);

        if (DBticket.isEmpty())
            throw new NoSuchElementException("Write-off ticket not found in database");

        WriteOffTicket ticket = DBticket.get();

        if (ticket.getState() != WriteOffTicketState.AWAITING)
            throw new Exception("Write-off ticket is not awaiting");

        Product product = productRepository.findByProductId(ticket.getProduct().getProductId());
        System.out.println("What is product? " + product);
        product.setState(ProductState.WRITTEN_OFF);
        List<SparePart> partList = product.getSpareParts();

        for (SparePart part : partList) {
           // maybe not needed if already got the right part::: SparePart foundPart = sparePartRepository.findById(part.getPart_id()).orElseThrow();
            part.setState(SparePartState.AVAILABLE);
            sparePartRepository.save(part);
        }

        productRepository.save(product);
        writeOffTicketRepository.delete(ticket);
    }

    @Override
    public void disApproveWriteOffTicket(String id) throws Exception {

        WriteOffTicket ticket = writeOffTicketRepository.findById(id).orElseThrow();

        if (ticket.getState() != WriteOffTicketState.AWAITING)
            throw new Exception("Write-off ticket is not awaiting");

        Product product = productRepository.findByProductId(ticket.getProduct().getProductId());

        product.setState(ProductState.DEFECTIVE);
        List<SparePart> partList = product.getSpareParts();

        sparePartRepository.deleteAll(partList);
        /*
        for (SparePart part : partList) {
            // maybe not needed if already got the right part::: SparePart foundPart = sparePartRepository.findById(part.getPart_id()).orElseThrow();
            // part.setState(SparePartState.AVAILABLE);
            sparePartRepository.delete(part);
        }

         */

        productRepository.save(product);
        writeOffTicketRepository.delete(ticket);

    }
}
