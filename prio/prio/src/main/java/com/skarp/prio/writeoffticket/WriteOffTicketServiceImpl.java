package com.skarp.prio.writeoffticket;

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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
    public WriteOffTicket createWriteOffTicket(@RequestBody WriteOffTicketForm woForm, String prod_id, String tech_id) {

        Optional<Product> DBproduct = productRepository.findById(prod_id);

        if (DBproduct.isEmpty())
            throw new NoSuchElementException("Item not found in database");

        Product product = DBproduct.get();

        if (product.getState() != ProductState.DEFECTIVE)
            throw new RuntimeException("Product state not applicable for write-off");

        List<SparePartType> sparePartTypes = WriteOffFormParser.parseTypes(woForm);

        product.setState(ProductState.IN_WRITEOFF);
        productRepository.save(product);

        WriteOffTicket ticket = new WriteOffTicket(product, tech_id);
        ticket.setReason(woForm.getReason());
        ticket.setTechnicianName(tech_id);

        /* Generate used spare-parts from the product under write-off and add to the ticket */
        for (SparePartType type : sparePartTypes) {
            UsedSparePart part = new UsedSparePart(product.getProductId(), product.getBrand(), product.getCategory(), product.getModel(), type,product.getCostPrice() / (double) sparePartTypes.size());
            part.setState(SparePartState.MARKED_FUNCTIONAL);
            sparePartRepository.save(part);
            ticket.addSparePart(part);
        }

        return writeOffTicketRepository.save(ticket);
    }

    @Override
    public List<WriteOffTicket> getAllWriteOffTickets() {
        return operations.findAll(WriteOffTicket.class);
    }

    @Override
    public WriteOffTicket getWriteOffTicketById(String woId) {

        Optional<WriteOffTicket> woTicket = writeOffTicketRepository.findById(woId);
        if (woTicket.isPresent()) {
            return woTicket.get();
        }
        throw new NoSuchElementException("Could not find writeoff-ticket");
    }

    @Override
    public void approveWriteOffTicket(String id) {
        Optional<WriteOffTicket> DBticket = writeOffTicketRepository.findById(id);

        if (DBticket.isEmpty())
            throw new NoSuchElementException("Write-off ticket not found in database");

        WriteOffTicket ticket = DBticket.get();

        if (ticket.getState() != WriteOffTicketState.AWAITING)
            throw new RuntimeException("Write-off ticket is not awaiting");

        Product product = productRepository.findById(ticket.getProduct().getId()).orElseThrow();

        List<SparePart> partList = ticket.getSpareParts();

        // update product cost price from added parts
        for (SparePart part : partList) {
           // not needed if we get the right part from the ticket part array: SparePart foundPart = sparePartRepository.findById(part.getPart_id()).orElseThrow();
            part.setState(SparePartState.AVAILABLE);
            product.setCostPrice(product.getCostPrice() + part.getCostPrice());

        }

        product.setState(ProductState.WRITTEN_OFF);

        sparePartRepository.saveAll(partList);
        productRepository.save(product);
        writeOffTicketRepository.delete(ticket); // Todo: consider whether we need to keep this for statistics
    }

    @Override
    public void disApproveWriteOffTicket(String id) {

        WriteOffTicket ticket = writeOffTicketRepository.findById(id).orElseThrow();

        if (ticket.getState() != WriteOffTicketState.AWAITING)
            throw new RuntimeException("Write-off ticket is not awaiting");

        Product product = productRepository.findById(ticket.getProduct().getId()).orElseThrow();

        product.setState(ProductState.DEFECTIVE);
        List<SparePart> partList = ticket.getSpareParts();

        //Remove all spareparts that were marked functional
        sparePartRepository.deleteAll(partList);
        ticket.getSpareParts().clear();


        productRepository.save(product);
        writeOffTicketRepository.delete(ticket);

    }
}
