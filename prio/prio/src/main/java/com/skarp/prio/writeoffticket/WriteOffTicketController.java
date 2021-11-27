package com.skarp.prio.writeoffticket;

import com.skarp.prio.products.Product;
import com.skarp.prio.products.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@CrossOrigin("*")
@RestController
public class WriteOffTicketController {

    @Autowired
    MongoOperations operations;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    WriteOffTicketRepository writeOffTicketRepository;

    private static int writeoffs = ThreadLocalRandom.current().nextInt(0,200); // Todo: find out what this is used for

    @GetMapping("/writeoffs/")
    public List<WriteOffTicket> writeOffTickets(@RequestParam(value = "brand") String brand,
                                                @RequestParam(value = "category") String category) {

        List<WriteOffTicket> result = operations.query(WriteOffTicket.class).matching(query(where("brand").is(brand).and("category").is(category))).all();
        return result;
    }

    @PostMapping("/products/{id}")
    public WriteOffTicket writeOffTicket(@RequestParam(value = "product_id") String product_id),
                                            @RequestParam(value = "name") String name)
    // Todo: Need information about functional spareparts from the product
    {
        List<Product> products; //= new ArrayList<>();
        Product product; // = new Product(); //"Lenovo", Category.LAPTOP,"E480","2016","17\"",2222,1111);
        // product.setProductId("697140000001");
        WriteOffTicket ticket; // = new WriteOffTicket(); //product, "prebuilt name");
        try {
            Query query = new Query(Criteria.where("productId").is(productId));
            products = operations.find(query, Product.class);
            if (products.size() > 1)
                throw new Exception("Found more than one product in database with productId: " + productId);
            if (products.isEmpty())
                throw new NoSuchElementException("Item not found in database");
            product = products.get(0);
            ticket = new WriteOffTicket(product, name);
            // operations.save(product); // Todo: find out why have both repository and operations
            productRepository.save(product);
            writeOffTicketRepository.save(ticket);
            System.out.println("ticketId = " + ticket.getId());
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Write-off ticket created with id: " + ticket.getId(), HttpStatus.CREATED);
    }

    // Todo: make get mappings for declining and approving write off tickets
}