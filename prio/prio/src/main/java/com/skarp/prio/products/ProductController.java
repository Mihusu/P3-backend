package com.skarp.prio.products;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;

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

/**
 * The {@code ProductController} overall aim is to find products based on product information. through
 * {@link ProductRepository}
 * information.
 * The {@code ProductController} achieves this through four essential functions.
 * 1. The {@code ProductController}  finds a list of parameter values from a product. These parameter values are bound
 * to the URL "/products".
 * 2. Creates a query that contains product information and finds products matching query information repository
 * The {@code ProductController} contains a list of information about a product.
 * The {@code ProductController} uses {@code RequestParam} and {@code GetMapping} to bind the information
 * The {@code ProductController}  will be used in the URL "/products".
 * The {@code ProductsController} contains the following information about a product: name, model, brand, category,
 * state and sort-by. Sort-by is used
 *
 * class represents a product. A {@code Product} object contains information about the:
 * ID of the product, an array containing sparepart(s) within the product, in store product ID, name of product,
 * model of product, year the product was produced, the brand of the product,the category of the product,
 * the specifications of the product, the date added to warehouse, the current state of the product {@link ProductState},
 * the sales price of the product, the cost price of the product,
 * the serial number of the product and the defective comment added to the product.
 *
 * This is an example of the creation of a {@code Product} object.
 *
 * <blockquote><pre>
 *     Product product = new Product("61a2b2f0a14da164b79664af");
 *     product.Array ???
 *     product.setProductId("697140000001");
 *     product.setName("Lenovo LAPTOP E480 2016 17");
 *     product.setModel("E480");
 *     product.setYear("2016");
 *     product.setBrand("Lenovo");
 *     product.setCategory(Category.LAPTOP);
 *     product.setSpecification("128GB White");
 *     product.setDateAdded("2021-11-26T23:00:00.000+00:00");
 *     product.setState(ProductState.DEFECTIVE);
 *     product.setSalesPrice("4321");
 *     product.setCostPrice("1274");
 *     product.setSerialNumber("352925111010833");
 *     product.setDefectiveComment("Den virker ikke... .");
 * </pre></blockquote>
 *
 * @author Team-Skarp
 * @see Product
 * @see ProductState
 * @see Category
 * @since ???
 */

@CrossOrigin("*")
@RestController
public class ProductController {

    @Autowired
    MongoOperations operations;

    @Autowired
    ProductRepository repository;
    /**
     * {@code GetMapping} with URL "/products"
     * Use {@code RequestParam} to
     *
     * @return a {@code LocalDate} specifying the date product added to warehouse.
     */
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


    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductsByID(@PathVariable String id) {

        try {
            if (repository.findById(id).isEmpty()) {
                String msg = "Repair not found with id: " + id; // Todo: get some meatballs for this juicy copy-pasta :P
                throw new NoSuchElementException(msg);
            }

            return new ResponseEntity<>(repository.findById(id).get(), HttpStatus.OK);
        }
        catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/products/{productId}/sparepart_types")
    public ResponseEntity<?> getCompatibleTypes(@PathVariable String productId) {

        Optional<Product> product = repository.findById(productId);

        if (product.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Product productModel = product.get();

        List<SparePartType> sparePartTypes = CompatibleSparePartTypeMap.conversionMap.get(productModel.getCategory());

        return new ResponseEntity<>(sparePartTypes, HttpStatus.OK);
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

    @PostMapping("/products/")
    public  ResponseEntity<?> uploadProduct(
            @RequestParam("product_id") String productId,
            @RequestParam("brand") String brand,
            @RequestParam("model") String model,
            @RequestParam("specification") String specification,
            @RequestParam("cost_price") Double costPrice,
            @RequestParam("sale_price") Double salePrice,
            @RequestParam("category") String category,
            @RequestParam("comment") String comment
    ) {
        Product product = new Product();
        product.setName(brand +" "+ category +" "+ model +" "+ specification);
        product.setProductId(productId);
        product.setBrand(brand);
        product.setModel(model);
        product.setSpecification(specification);
        product.setCostPrice(costPrice);
        product.setSalesPrice(salePrice);
        product.setDefectiveComment(comment);

        switch (category.trim().toLowerCase()) { //TODO: Make conversion from string to enum with valueOf
            case "iphone" -> product.setCategory(Category.IPHONE);
            case "macbook" -> product.setCategory(Category.MACBOOK);
            case "ipad" -> product.setCategory(Category.IPAD);
            case "laptop" -> product.setCategory(Category.LAPTOP);
            case "smartphone" -> product.setCategory(Category.SMARTPHONE);
            case "tablet" -> product.setCategory(Category.TABLET);
        }

        try {
            repository.save(product);
            System.out.println(product.toString());
            return new ResponseEntity<>("Product created:"+product.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.EXPECTATION_FAILED);
        }
    }

}

