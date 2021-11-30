package com.skarp.prio.writeoffticket;

import com.skarp.prio.products.Product;
import com.skarp.prio.products.ProductRepository;
import com.skarp.prio.products.ProductState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
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

    @Autowired
    WriteOffTicketServiceImpl writeOffTicketService;

    private static int writeoffs = ThreadLocalRandom.current().nextInt(0,200); // Todo: find out what this is used for

    @GetMapping("/writeoffs")
    public List<WriteOffTicket> writeOffTickets(@RequestParam(value = "brand") String brand,
                                                @RequestParam(value = "category") String category) {

        List<WriteOffTicket> result = operations.query(WriteOffTicket.class).matching(query(where("brand").is(brand).and("category").is(category))).all();
        return result;
    }

    @PostMapping("/writeoffs/create")
    public ResponseEntity<?> createWriteOffTicket(@RequestBody WriteOffTicketForm woForm, @RequestParam(value = "prod_id") String prod_id, @RequestParam(value = "tech_id") String tech_id)
    {
        try {
            System.out.println(woForm.reason);
            System.out.println(woForm.markedParts);

            System.out.println("Before creating WOT");
            writeOffTicketService.createWriteOffTicket(woForm, prod_id, tech_id);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}