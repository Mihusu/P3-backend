package com.skarp.prio;

import com.skarp.prio.products.Product;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

public class MongoOperationsTest {

    MongoOperations operations;
    private Product iphone, laptop;

    @BeforeEach
    void setup() {

        operations.remove(new Query(), Product.class);//TODO: NOT FINISHED
    }
}
