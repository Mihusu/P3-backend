package com.skarp.prio.repairs;

import com.skarp.prio.products.Product;
import com.skarp.prio.products.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.NoSuchElementException;

@RestController
public class RepairController {

    @Autowired
    RepairRepository repairRepository;

    @Autowired
    ProductRepository productRepository;

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

}
