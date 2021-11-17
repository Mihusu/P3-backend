package com.skarp.prio.products;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


@RestController
public class ProductController {

    MongoOperations operations;
    private static int price = ThreadLocalRandom.current().nextInt(0,200);

    @GetMapping("/product/search")
    public List<Product> product(@RequestParam(value = "brand", defaultValue = "Apple") String brand,
                                 @RequestParam(value = "category", defaultValue = "iPhone") String category) {

        List<Product> result = operations.query(Product.class).matching(query(where("brand").is(brand).and("category").is(category))).all();

        return result;
    };
}

