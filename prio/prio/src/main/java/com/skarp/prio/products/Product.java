package com.skarp.prio.products;

import com.skarp.prio.spareparts.SparePart;
import org.springframework.data.annotation.Id;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * The {@code Product} class represents a product. A {@code Product} object contains information about the:
 * ID of the product, an array containing sparepart(s) within the product, in store product ID, name of product,
 * model of product, year the product was produced, the brand of the product,the category of the product,
 * the specifications of the product, the date added to warehouse, the current state of the product {@link ProductState},
 * the sales price of the product, the cost price of the product,
 * the serial number of the product and the defective comment added to the product.
 *
 * <p>
 *  The product ID, name of product, model of product, year the product was made, the brand of the product, the category
 *  of the product, the specifications of the product, the sales price of the product and the serial number of the
 *  product are not mutable.
 *  The date added to warehouse, the current state of the product, the cost price of the product and the defective
 *  comment added to the product are mutable.
 *  The products category is defined according to the enum {@link Category}.
 *  The products state is defined according to the enum {@link ProductState}.
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

public class Product {
    /** Contains the id of the product*/
    @Id
    private String id;
    /** Array contains the sparepart(s) within the product*/
    private ArrayList<SparePart> spareParts = new ArrayList<>();
    /** Contains in store product ID*/
    private String productId;       // In store product ID
    /** Contains name of product*/
    private String name;
    /** Contains model of product*/
    private String model;           // Ex: Pro, E480, 8, 9, 11 Pro
    /** Contains year product was produced*/
    private String year;            // 2016
    /** Contains braind of product*/
    private String brand;           // Apple, Lenovo
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
     * productId, brand, category, model, year, specification, sales price, cost price.
     * {@param name}, a {@code String} object containing the name of the student. // Todo: student? check comments for more students
     */
    public Product(String productId, String brand, Category category, String model, String year, String specification, double salesPrice, double costPrice) {
        this.name = brand + " " + category + " " + model + " " + year + " " + specification;
        this.productId = productId;
        this.brand = brand;
        this.category = category;
        this.model = model;
        this.year = year;
        this.specification = specification;
        this.salesPrice = salesPrice;
        this.costPrice = costPrice;
        this.dateAdded = LocalDate.now();
        this.state = ProductState.DEFECTIVE;
    }
    /**
     * Initializes a newly created {@code Product} object. Information
     * about the age, the city and the university is not provided.
     * @param name, a {@code String} object containing the name of the student.
     */
    public Product(){
        this.dateAdded = LocalDate.now();
    }

    public LocalDate getDateAdded(){
        return this.dateAdded;
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
     * @return a {@code String} specifying the name of the product.
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
     * @return a {@code String} specifying the cost price of the product.
     */
    public double getCostPrice() {return this.costPrice;}
    /**
     * Getter for the profit sum of a product.
     * Subtracts the cost price with the sales price to determine the profit sum.
     * @return a {@code String} specifying the cost price of the product.
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
     * Getter for the sparepart(s) of a product.
     * @return a {@code boolean} specifying if a sparepart is found.
     * The sparepart is then added to sparepart array list.
     */
    public boolean addSparePart(SparePart sp) {
        return this.spareParts.add(sp);
    }
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
     * Getter for the year produced of a product.
     * @return a {@code String} specifying the year of the product.
     */
    public String getYear() {
        return this.year;
    }
    /**
     * Getter for the state of a product.
     * @return a {@link ProductState} specifying the state of the product.
     */
    public ProductState getState() {
        return this.state;
    }
    /**
     * Getter for the cost price of a product.
     * If cost price is bigger or equals to 0 then.
     * @return a {@code true boolean} specifying the cost price of the product.
     * If the cost price is less then 0 then @return a {@code false boolean}.
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
    public void setState(ProductState state) {
        this.state = state;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setSalesPrice(double salesPrice) {
        this.salesPrice = salesPrice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSerialnumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
    
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductId() {return productId;}

    public String getDefectiveComment() {
        return defectiveComment;
    }

    public void setDefectiveComment(String defectiveComment) {
        this.defectiveComment = defectiveComment;
    }
}