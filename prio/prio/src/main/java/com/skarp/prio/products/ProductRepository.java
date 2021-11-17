package com.skarp.prio.products;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    //public Product getProductByName(String name);
    //Do stuff....s
    Product getProductByName(String name);
}
