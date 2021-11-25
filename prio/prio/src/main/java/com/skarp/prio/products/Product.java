package com.skarp.prio.products;

import com.skarp.prio.spareparts.SparePart;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Product {
    @Id
    private String id;
    private ArrayList<SparePart> spareParts = new ArrayList<>();
    
    private String itemID;
    private String name;
    private String model;           // Ex: Pro, E480, 8, 9, 11 Pro
    private String year;            // 2016
    private String brand;           // Apple, Lenovo
    private Category category;      // Smartphone (and iPhone), Laptop, MacBook
    private String specification;   //Ex. 128gb, white
    private LocalDate dateAdded;  //date added to the warehouse
    private long storageTime;
    private ProductState state;
    private double salesPrice;
    private double costPrice;
    private String serialNumber;            // 356571101513554

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Product{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public Product(String brand, Category category, String model, String year, String specification, double salesPrice, double costPrice) {
        this.name = brand + " " + category + " " + model + " " + year + " " + specification;
        this.brand = brand;
        this.category = category;
        this.model = model;
        this.year = year;
        this.specification = specification;
        this.salesPrice = salesPrice;
        this.costPrice = costPrice;
        this.dateAdded = LocalDate.now();
        this.state = ProductState.DEFECTIVE;
        this.storageTime = calcStorageTime();
    }

    public Product(){

    }

    public LocalDate getDateAdded(){
        return this.dateAdded;
    }

    public String getName() {
        return this.name;
    }

    public long getStorageTime(){
        this.storageTime = calcStorageTime();
        return this.storageTime;
    }

    public long calcStorageTime(){
        return ChronoUnit.DAYS.between(this.dateAdded, LocalDate.now());
    }

    public double getSalesPrice() {
        return this.salesPrice;
    }

    public double getCostPrice() {return this.costPrice;}

    public double getProfitSum(){return this.salesPrice - this.costPrice;}

    //returns profit margin in percent
    public double getProfitMargin(){return (1 - ( this.costPrice / this.salesPrice)) * 100;}

    public boolean addSparePart(SparePart sp) {
        return this.spareParts.add(sp);
    }

    public String getBrand() {
        return this.brand;
    }

    public Category getCategory() {
        return this.category;
    }

    public String getModel() {
        return this.model;
    }

    public String getYear() {
        return this.year;
    }

    public ProductState getState() {
        return this.state;
    }

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
    
    public void setItemID(String itemID) {
        this.itemID = itemID;
    }
}