package com.skarp.prio.products;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * The {@code Product} class represents a product. A {@code Product} object contains information about the:
 * ID of the product, an array containing sparepart(s) within the product, in store product ID, name of product,
 * model of product, the brand of the product,the category of the product,
 * the specifications of the product, the date added to warehouse, the current state of the product {@link ProductState},
 * the sales price of the product, the cost price of the product,
 * the serial number of the product and the defective comment added to the product.
 *
 * <p>
 *  The product ID, name of product, model of product, the brand of the product, the category
 *  of the product, the specifications of the product, the sales price of the product and the serial number of the
 *  product are not mutable.
 *  The date added to warehouse, the current state of the product, the cost price of the product and the defective
 *  comment added to the product are mutable.
 *  The product's category is defined according to the enum {@link Category}.
 *  The product's state is defined according to the enum {@link ProductState}.
 *  <p>
 *
 * This is an example of the creation of a {@code Product} object.
 *
 * <blockquote><pre>
 *
 *     Product product = new Product("61a2b2f0a14da164b79664af");
 *     product.Array ???
 *     product.setProductId("697140000001");
 *     product.setName("Lenovo LAPTOP E480 2016 17");
 *     product.setModel("E480");
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

public class Product {
    /** Contains the id of the product*/
    @Id
    private String id;
    /** Contains in store product ID*/
    private String productId;       // In store product ID
    /** Contains name of product*/
    private String name;
    /** Contains brand of product*/
    private String brand;           // Apple, Lenovo
    /** Contains model of product*/
    private String model;           // Ex: Pro, E480, 8, 9, 11 Pro
    /** Contains category of product*/
    private Category category;      // Smartphone (and iPhone), Laptop, MacBook
    /** Contains specifications of product*/
    private String specification;   //Ex. 128gb, white
    /** Contains date product added to warehouse*/
    private LocalDate dateAdded;  //date added to the warehouse
    /** Contains state of product*/
    private ProductState state;
    /** Contains sold price of product(DKK)*/
    private double salesPrice;
    /** Contains cost price of product(DKK)*/
    private double costPrice;
    /** Contains serialnumber of product*/
    private String serialNumber;            // 356571101513554
    /** Contains defective comment given to product*/
    private String defectiveComment;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Product{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
    /**
     * Initializes a newly created {@code Product} object.
     * It contains the following information about the product:
     * productId, brand, category, model, specification, sales price, cost price.
     * The {@link ProductState} is set to {@code DEFECTIVE}.
     * @param ???, a {@code ???} containing ???.
    */
    // Seems it doesn't need this @PersistenceConstructor even though it has a no-arg constructor
    public Product(String productId, String brand, Category category, String model, String specification, double salesPrice, double costPrice) {
        this.name = brand + " " + category + " " + model + " " + specification;
        this.productId = productId;
        this.brand = brand;
        this.category = category;
        this.model = model;
        this.specification = specification;
        this.salesPrice = salesPrice;
        this.costPrice = costPrice;
        this.dateAdded = LocalDate.now();
        this.state = ProductState.DEFECTIVE;
    }


    public Product(){
        this.dateAdded = LocalDate.now();
    }
    /**
     * Getter for the date product added to warehouse.
     * @return a {@code LocalDate} specifying the date product added to warehouse.
     */
    public LocalDate getDateAdded(){
        return this.dateAdded;
    }
    /**
     * Setter for the date product added to warehouse.
     * date product added to warehouse is set to current date.
     * @param dateAdded, a {@code LocalDate} specifying the current date.
     */
    public void setDateAdded(LocalDate dateAdded){
        this.dateAdded = LocalDate.now();
    }
    /**
     * Getter for the ID of a product.
     * @return a {@code String} specifying the ID of the product.
     */
    public String getId() {
        return this.id;
    }
    /**
     * Getter for the name of a product.
     * @return a {@code String} specifying the name of the product.
     */
    public String getName() {
        return this.name;
    }
    /**
     * Getter for the storage time of a product.
     * Finds the days between date product added to warehouse and date now to determine storage time.
     * @return a {@code Long} specifying the name of the product.
     */
    public long getStorageTime(){
        return ChronoUnit.DAYS.between(this.dateAdded, LocalDate.now());
    }
    /**
     * Getter for the sales price of a product.
     * @return a {@code double} specifying the sales price of the product.
     */
    public double getSalesPrice() {
        return this.salesPrice;
    }
    /**
     * Getter for the cost price of a product.
     * @return a {@code double} specifying the cost price of the product.
     */
    public double getCostPrice() {return this.costPrice;}
    /**
     * Getter for the profit sum of a product.
     * Subtracts the cost price with the sales price to determine the profit sum.
     * @return a {@code double} specifying the cost price of the product.
     */
    public double getProfitSum(){return this.salesPrice - this.costPrice;}
    /**
     * Getter for the cost price of a product.
     * Divides the sales price with the cost price to determine profit margin ratio.
     * 1 subtracted with the profit margin ratio multiplied by 100 to determine to the profit margin.
     * @return a {@code double} specifying the profit margin of the product.
     */
    public double getProfitMargin(){return (1 - ( this.costPrice / this.salesPrice)) * 100;}
    /**
     * Getter for the brand of a product.
     * @return a {@code String} specifying the brand of the product.
     */
    public String getBrand() {
        return this.brand;
    }
    /**
     * Getter for the category of a product.
     * @return a {@link Category} specifying the category of the product.
     */
    public Category getCategory() {
        return this.category;
    }
    /**
     * Getter for the model of a product.
     * @return a {@code String} specifying the model of the product.
     */
    public String getModel() {
        return this.model;
    }

    /**
     * Getter for the state of a product.
     * @return a {@link ProductState} specifying the state of the product.
     */
    public ProductState getState() {
        return this.state;
    }
    /**
     * Getter and setter for the cost price of a product.
     * If cost price is bigger or equals to 0 then @return a true {@code boolean} that specifies the cost price
     * of the product.
     * If the cost price is less then 0 then @return a false {@code boolean}.
     */
    public boolean setCostPrice(double costPrice) {
        if (costPrice >= 0) {
            this.costPrice = costPrice;
            return true;
        }
        else {
            return false;

        }
    }
    /**
     * Setter for the state of the product.
     * @param state, a {@link ProductState} specifying the state of the product.
     */
    public void setState(ProductState state) {
        this.state = state;
    }
    /**
     * Setter for the model of the product.
     * @param model, a {@code String} specifying the model of the product.
     */
    public void setModel(String model) {
        this.model = model;
    }
    /**
     * Setter for the specifications of the product.
     * @param specification, a {@code String} specifying the specification of the product.
     */
    public void setSpecification(String specification) {
        this.specification = specification;
    }
    /**
     * Setter for the specifications of the product.
     * @param brand, a {@code String} specifying the brand of the product.
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }
    /**
     * Setter for the specifications of the product.
     * @param category, an enum from {@link Category} specifying the category of the product.
     */
    public void setCategory(Category category) {
        this.category = category;
    }
    /**
     * Setter for the specifications of the product.
     * @param salesPrice, a {@code double} specifying the brand of the product.
     */
    public void setSalesPrice(double salesPrice) {
        this.salesPrice = salesPrice;
    }
    /**
     * Setter for the specifications of the product.
     * @param name, a {@code String} specifying the name of the product.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Setter for the specifications of the product.
     * @param serialNumber, a {@code String} specifying the serialnumber of the product.
     */
    public void setSerialnumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
    /**
     * Setter for the productID of the product.
     * @param productId, a {@code String} specifying the productID of the product.
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }
    /**
     * Getter for the productID of a product.
     * @return a {@code String} specifying the productID of the product.
     */
    public String getProductId() {return productId;}
    /**
     * Getter for the defective comment of a product.
     * @return a {@code String} specifying the defective comment of the product.
     */
    public String getDefectiveComment() {
        return defectiveComment;
    }
    /**
     * Setter for the defective comment of the product.
     * @param defectiveComment, a {@code String} detailing the defects of the product.
     */
    public void setDefectiveComment(String defectiveComment) {
        this.defectiveComment = defectiveComment;
    }
}