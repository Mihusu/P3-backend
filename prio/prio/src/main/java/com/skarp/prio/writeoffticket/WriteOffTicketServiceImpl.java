package com.skarp.prio.writeoffticket;

import com.skarp.prio.products.Product;
import com.skarp.prio.products.ProductRepository;
import com.skarp.prio.products.ProductState;
import com.skarp.prio.spareparts.Enums.SparePartState;
import com.skarp.prio.spareparts.Enums.SparePartType;
import com.skarp.prio.spareparts.UsedSparePart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class WriteOffTicketServiceImpl implements WriteOffTicketService{

    @Autowired
    WriteOffTicketRepository writeOffTicketRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public void createWriteOffTicket(@RequestBody WriteOffTicketForm woForm, String prod_id, String tech_id) {

        Product product = productRepository.findByProductId(prod_id);

        if (product == null)
            throw new NoSuchElementException("Item not found in database");

        if (product.getState() == ProductState.IN_WRITEOFF)
            throw new IllegalStateException("Product is already in write-off");

        List<SparePartType> sparePartTypes = WriteOffFormParser.parseTypes(woForm);

        sparePartTypes.forEach(type -> {
            product.addSparePart(new UsedSparePart(product, type, product.getCostPrice() / (double) sparePartTypes.size()));
        });

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
