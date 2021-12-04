package com.skarp.prio.spareparts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
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

@CrossOrigin("*")
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
     *     private String brand;       // Apple, Lenovo
     *     private String category;    // Smartphone (and iPhone), Laptop, MacBook
     */

    @PostMapping("/spareparts")
    public  ResponseEntity<?> uploadSparePart(
      @RequestParam("brand") String brand,
      @RequestParam("category") String category,
      @RequestParam("model") String model,
      @RequestParam("grade") String grade,
      @RequestParam("type") String type,
      @RequestParam("costPrice") Double costPrice,
      @RequestParam("sku") String sku
    ) {
        System.out.println("uploadSparePart mapping ramt");

        try {
            SparePart sparePart = sparePartService.uploadSparePart(brand, category, model, grade, type, costPrice, sku);

            return new ResponseEntity<>("Spare part created: "+ sparePart.getName(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/spareparts")
    public ResponseEntity<?> getSparePartList(@RequestParam(required = false, value="name") String name,
                                              @RequestParam(required = false, value="brand") String brand,
                                              @RequestParam(required = false, value="category") String category,
                                              @RequestParam(required = false, value="model") String model,
                                              @RequestParam(required = false, value="type") String type,
                                              @RequestParam(required = false, value="state") String state,
                                              @RequestParam(required = false, value="sortBy") String sortBy) {
        // Feed parameters to part finder method
        try {
            return new ResponseEntity<>(sparePartService.getSparePartList(name, brand, category, model, type, state, sortBy), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    /* moved to service layer
    @GetMapping("/sparepartsold") // Todo: find out if this is still used or should be changed to getSparePartList
    public List<SparePart> spareparts(
            @RequestParam(required = false, value="name") String name,
            @RequestParam(required = false, value="type") String type,
            @RequestParam(required = false, value="model") String model,
            @RequestParam(required = false, value="brand") String brand,
            @RequestParam(required = false, value="category") String category,
            @RequestParam(required = false, value="state") String state,
            @RequestParam(required = false, value="sortBy") String sortBy
    ) {

        // Create Empty Query
        Query sparepartQuery = new Query();


        // Check for Params and add to Criteria
        if (name != null) {sparepartQuery.addCriteria(Criteria.where("name").regex(name));}
        if (brand != null) {sparepartQuery.addCriteria(Criteria.where("brand").is(brand));}
        if (category != null) {
            //Convert to enum type
            Category category1 = Category.valueOf(category.toUpperCase());
            sparepartQuery.addCriteria(Criteria.where("category").is(category1));}
        if (model != null) {sparepartQuery.addCriteria(Criteria.where("model").is(model));}
        if (type != null) {sparepartQuery.addCriteria(Criteria.where("type").is(type));}
        if (state != null) {sparepartQuery.addCriteria(Criteria.where("state").is(state));}
        if (sortBy != null) {sparepartQuery.with(Sort.by(Sort.Direction.ASC, sortBy));}

        System.out.println("sparepartQuery = " + sparepartQuery);

        // Find Products matching Query
        try {
            return operations.find(sparepartQuery, SparePart.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

     */


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
