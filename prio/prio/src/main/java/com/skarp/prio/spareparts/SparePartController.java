package com.skarp.prio.spareparts;

import com.skarp.prio.products.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.NoSuchElementException;

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


    @GetMapping("/spareparts") // Todo: find out if this is still used or should be changed to getSparePartList
    public List<SparePart> spareparts(
            @RequestParam(required=false, value="name") String name,
            @RequestParam(required=false, value="type") String type,
            @RequestParam(required = false, value="model") String model,
            @RequestParam(required=false, value="brand") String brand,
            @RequestParam(required=false, value="category") String category,
            @RequestParam(required = false, value="state") String state,
            @RequestParam(required = false, value="sortBy") String sortBy
    ) {

        // Create Empty Query
        Query sparepartQuery = new Query();


        // Check for Params and add to Criteria
        if (name != null) {sparepartQuery.addCriteria(Criteria.where("name").regex(name));}
        if (type != null) {sparepartQuery.addCriteria(Criteria.where("type").is(type));}
        if (model != null) {sparepartQuery.addCriteria(Criteria.where("model").is(model));}
        if (brand != null) {sparepartQuery.addCriteria(Criteria.where("brand").is(brand));}
        if (category != null) {
            //Convert to enum type
            Category category1 = Category.valueOf(category.toUpperCase());
            sparepartQuery.addCriteria(Criteria.where("category").is(category1));}
        if (state != null) {sparepartQuery.addCriteria(Criteria.where("state").is(state));}
        if (sortBy != null) {sparepartQuery.with(Sort.by(Sort.Direction.ASC, sortBy));}

        // Find Products matching Query
        try {
            return operations.find(sparepartQuery, SparePart.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }



    @GetMapping("/spareparts/{id}")
    public ResponseEntity<?> getSparePartsByID(@PathVariable String id) {
        try {
            return new ResponseEntity<>(sparePartService.getSparePartByID(id), HttpStatus.OK);
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/spareparts/file")
    public ResponseEntity<Object> uploadSpareParts(@RequestParam("File") MultipartFile multipart) {
        if (!multipart.isEmpty()) {
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(
                            new ByteArrayInputStream(multipart.getBytes())))) {
                List<NewSparePart> sparePartList = reader.lines().map(SparePartParser::parse).toList();

                sparePartRepository.saveAll(sparePartList);
            } catch (IOException e) {
                return new ResponseEntity<>("The uploaded file could not be read.",HttpStatus.EXPECTATION_FAILED);
            }
            return new ResponseEntity<>("The file uploaded successfully.",HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("The uploaded file was empty", HttpStatus.NO_CONTENT);
        }
    }
}
