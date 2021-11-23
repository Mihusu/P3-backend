package com.skarp.prio.products;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")
@RestController
public class ProductController {

    @Autowired
    MongoOperations operations;

    @Autowired
    ProductRepository repository;

    @GetMapping("/products")
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
        if (name != null) {productQuery.addCriteria(Criteria.where("name").regex(name));}
        if (model != null) {productQuery.addCriteria(Criteria.where("model").is(model));}
        if (brand != null) {productQuery.addCriteria(Criteria.where("brand").is(brand));}
        if (category != null) {productQuery.addCriteria(Criteria.where("category").is(category));}
        if (state != null) {productQuery.addCriteria(Criteria.where("state").is(state));}
        if (sortBy != null) {productQuery.with(Sort.by(Sort.Direction.ASC, sortBy));}

        // Find Products matching Query
        return operations.find(productQuery, Product.class);
    }

    // File location
    @Value("${file.upload-dir}")
    String FILE_DIRECTORY;

    @PostMapping("/products/file")
    public ResponseEntity<Object> uploadProducts(@RequestParam("File") MultipartFile multipart) throws IOException {

        if (!multipart.isEmpty()) {
            // Create File handler
            ProductFileHandler handler = new ProductFileHandler();
            handler.createFile(FILE_DIRECTORY, multipart);

            try (BufferedReader reader = Files.newBufferedReader(handler.getPath())) {
                List<Product> productList = reader.lines().map(ProductParser::parse).toList();

               for (Product product: productList) {
                   // Save all Products
                   repository.save(product);

                   // test
                   System.out.println(product);
               }

            } catch (IOException e) {
                System.out.println("Unable to read the file");
            }

            // Delete file after usage
            handler.deleteFile();

            // Return Reponse
            return new ResponseEntity<Object>("The File Uploaded Successfully", HttpStatus.OK); //Todo : make a proper response text

        } else {
            // Return Reponse
            return new ResponseEntity<Object>("The File Uploaded to server was empty", HttpStatus.NO_CONTENT);
        }
    }
}

