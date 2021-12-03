package com.skarp.prio.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


public class ProductRepositoryImpl implements ProductRepositoryService {

    @Autowired
    MongoOperations operations;

    @Override
    public Product findByProductID(String productId) {

        Query query = new Query(Criteria.where("productId").is(productId));
        return operations.findOne(query, Product.class);
    }
}
