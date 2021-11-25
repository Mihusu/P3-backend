package com.skarp.prio.spareparts;

import com.skarp.prio.products.Category;
import com.skarp.prio.spareparts.Enums.SparePartState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SparePartController {

    @Autowired
    SparePartRepository sparePartRepository;

    @Autowired
    SparePartService sparePartService;

    @Autowired
    MongoOperations operations;

    /**
     * private String type;        // Ex: Battery, Screen
     *     private String model;       // Ex: Pro, E480
     *     private String modelYear;   // 2016
     *     private String brand;       // Apple, Lenovo
     *     private String category;    // Smartphone (and iPhone), Laptop, MacBook
     */


    @GetMapping("/spareparts/")
    public ResponseEntity<?> getSparePartList(@RequestParam(required = false, value = "category") String category,
                                              @RequestParam(required = false, value = "model") String model,
                                              @RequestParam(required = false, value = "year") String year,
                                              @RequestParam(required = false, value = "brand") String brand,
                                              @RequestParam(required = false, value = "state") SparePartState state)
    {
        // Ensure category is passed correctly as upper case before conversion to enum
        Category categoryEnum = Category.valueOf(category.toUpperCase());
        return new ResponseEntity<>(sparePartService.getSparePartList(categoryEnum, model, year, brand, state), HttpStatus.OK);

    }


}
