package com.skarp.prio.products;

import com.skarp.prio.repairs.Repair;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    //Do stuff....s
    Product findProductByProdID(String prod_id);
}
