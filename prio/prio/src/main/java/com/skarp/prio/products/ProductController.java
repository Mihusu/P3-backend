package com.skarp.prio.products;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private static final String template = "This is a, %s!";
    private static int price = ThreadLocalRandom.current().nextInt(0,200);

    @GetMapping("/product")
    public Product product(@RequestParam(value = "name", defaultValue = "iPhone") String name) {
        return new Product(String.format(template, name), price);
    };
}

