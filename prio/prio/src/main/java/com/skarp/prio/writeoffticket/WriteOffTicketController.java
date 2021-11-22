package com.skarp.prio.writeoffticket;

import com.skarp.prio.products.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@CrossOrigin("*")
@RestController
public class WriteOffTicketController {

    @Autowired
    MongoOperations operations;

    @Autowired
    ProductRepository repository;

    private static int writeoffs = ThreadLocalRandom.current().nextInt(0,200);

    @GetMapping("/writeoffs/")
    public List<WriteOffTicket> writeOffTickets(@RequestParam(value = "brand") String brand,
                                                @RequestParam(value = "category") String category) {

        List<WriteOffTicket> result = operations.query(WriteOffTicket.class).matching(query(where("brand").is(brand).and("category").is(category))).all();
        return result;
    };
}
