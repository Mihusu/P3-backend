package com.skarp.prio.repairs;

import com.skarp.prio.products.Product;
import com.skarp.prio.products.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.websocket.server.PathParam;
import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class RepairController {

    @Autowired
    RepairRepository repairRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MongoOperations operations;

    @GetMapping("/repairs")
    public List<Repair> getRepairList(@RequestParam(required = false, value = "state") RepairState state)
    {

        Query repairQuery = new Query();

        if (state != null) {repairQuery.addCriteria(Criteria.where("state").is(state));}

        List<Repair> repairList = operations.find(repairQuery, Repair.class);

        return repairList;
    }

    @PostMapping("/repairs")
    public ResponseEntity<?> createRepair(@RequestParam(required = true, value = "prod_id") String prod_id,
                                          @RequestParam(required = false, value = "tech_id") String tech_id,
                                          UriComponentsBuilder uriComponentsBuilder) {
        Product product;
        Repair repair;

        try {
            product = productRepository.findById(prod_id).get();

        } catch (NoSuchElementException e) { //TODO: Should have an error handler
            System.out.println("Error occured: " + e.getMessage());
            return ResponseEntity.internalServerError().body(e);
        };

        repair = new Repair(product);

        productRepository.save(product);
        repairRepository.save(repair);

        //Builds URI path
        UriComponents uriComponents =
                uriComponentsBuilder.path("/repairs/{id}").buildAndExpand(repair.getId());

        URI uri = uriComponents.toUri();

        return ResponseEntity.created(uri).build();

    }

    @GetMapping("/repairs/{id}")
    public Repair getRepairByID(@PathVariable String id) {

        return repairRepository.findById(id).get();
    }

    @PostMapping("/repairs/{id}")
    public ResponseEntity<?> updateRepair(@PathVariable String id,
                             @RequestParam(required = true, value = "func") String func) {

        Repair repair = repairRepository.findById(id).get();

        try {

            if (func.equals("resume")) {

                repair.resumeRepair();

            } else if (func.equals("pause")) {

                repair.pauseRepair();

            } else if (func.equals("finish")) {

                repair.finishRepair();

            } else {
                System.out.println("Bad request");
                return ResponseEntity.badRequest().build();
            }
        } catch (IllegalRepairOperationException e) {
            String msg = "Illegal repair operation: " + e.getMessage(); //TODO: Needs error handling
            return ResponseEntity.internalServerError().body(msg);
        }

        repairRepository.save(repair);
        productRepository.save(repair.getProduct());

        return ResponseEntity.ok().build();

    }

}
