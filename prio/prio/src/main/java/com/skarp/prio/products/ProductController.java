package com.skarp.prio.products;

import java.io.*;
import java.util.List;

import com.skarp.prio.spareparts.Enums.SparePartType;
import org.springframework.beans.factory.annotation.Autowired;
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
        if (category != null) {
            //Convert to enum type
            Category category1 = Category.valueOf(category.toUpperCase());
            productQuery.addCriteria(Criteria.where("category").is(category1));}
        if (state != null) {productQuery.addCriteria(Criteria.where("state").is(state));}
        if (sortBy != null) {productQuery.with(Sort.by(Sort.Direction.ASC, sortBy));}

        // Find Products matching Query
        try {
            return operations.find(productQuery, Product.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    @PostMapping("/products/file")
    public ResponseEntity<Object> uploadProducts(@RequestParam("File") MultipartFile multipart) throws IOException {
        if (!multipart.isEmpty()) {
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(multipart.getBytes())))) {
                List<Product> productList = reader.lines().map(ProductParser::parse).toList();

               for (Product product: productList) {
                   // Save all Products
                   repository.save(product);
                   System.out.println(product);
               }
            } catch (IOException e) {
                return new ResponseEntity<Object>("The File Uploaded, could not be read", HttpStatus.EXPECTATION_FAILED);
            }

            // Return Reponse
            return new ResponseEntity<Object>("The File Uploaded Successfully", HttpStatus.CREATED); //Todo : make a proper response text
        } else {
            // Return Reponse
            return new ResponseEntity<Object>("The File Uploaded to server was empty", HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/products/{productId}/sparepart_types")
    public ResponseEntity<?> getCompatibleTypes(@PathVariable String productId) {

        Product product = repository.findByProductID(productId);

        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<SparePartType> sparePartTypes = CompatibleSparePartTypeMap.conversionMap.get(product.getCategory());

        return new ResponseEntity<>(sparePartTypes, HttpStatus.OK);
    }
}

