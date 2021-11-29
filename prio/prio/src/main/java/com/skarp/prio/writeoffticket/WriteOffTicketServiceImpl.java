package com.skarp.prio.writeoffticket;

import com.skarp.prio.products.Product;
import com.skarp.prio.products.ProductRepository;
import com.skarp.prio.spareparts.Enums.SparePartType;
import com.skarp.prio.spareparts.UsedSparePart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class WriteOffTicketServiceImpl implements WriteOffTicketService{

    @Autowired
    WriteOffTicketRepository writeOffTicketRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public WriteOffTicket createWriteOffTicket(@RequestBody WriteOffTicketForm woForm, String prod_id, String tech_id) {

        Product product = productRepository.findByProductID(prod_id);

        List<SparePartType> sparePartTypes = WriteOffFormParser.parseTypes(woForm);

        sparePartTypes.forEach(type -> {
            product.addSparePart(new UsedSparePart(product, type, product.getCostPrice() / (double) sparePartTypes.size()));
        });

        //Needs additional work
        return null;

    }

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
