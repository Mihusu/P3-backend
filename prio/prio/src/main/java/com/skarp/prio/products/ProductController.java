package com.skarp.prio.products;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    MongoOperations operations;

    @Autowired
    ProductRepository repository;

    @GetMapping("/products/")
    public List<Product> product(
            @RequestParam(required=false, value="name") String name,
            @RequestParam(required=false, value="model") String model,
            @RequestParam(required=false, value="brand") String brand,
            @RequestParam(required=false, value="category") String category,
            @RequestParam(required = false, value="state") String state,
            @RequestParam(required = false, value="sortBy") String sortBy
    ) {

        // Create Empty Query
        Query productQuery = new Query();

        // Check for Params and add to Criteria
        if (name != null) {productQuery.addCriteria(Criteria.where("name").in(name));}
        if (model != null) {productQuery.addCriteria(Criteria.where("model").is(model));}
        if (brand != null) {productQuery.addCriteria(Criteria.where("brand").is(brand));}
        if (category != null) {productQuery.addCriteria(Criteria.where("category").is(category));}
        if (state != null) {productQuery.addCriteria(Criteria.where("state").is(state));}
        if (sortBy != null) {productQuery.with(Sort.by(Sort.Direction.ASC, sortBy));}

        // Find Products matching Query
        List<Product> foundProducts = operations.find(productQuery, Product.class);

        return foundProducts;
    }
}

