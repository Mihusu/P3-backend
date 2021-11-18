package com.skarp.prio.products;

import com.skarp.prio.Category;
import com.skarp.prio.spareparts.SparePart;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class Product {
    @Id
    private String id;
    private ArrayList<SparePart> spareParts = new ArrayList<>();
    private String name;
    private String model;           // Ex: Pro, E480, 8, 9, 11 Pro
    private String year;            // 2016
    private String brand;           // Apple, Lenovo
    private Category category;      // Smartphone (and iPhone), Laptop, MacBook
    private String specification;   //Ex. 128gb, white
    private ProductState state;
    private final double salesPrice;
    private double costPrice;


    public Product(String brand, Category category, String model, String year, String specification, double salesPrice, double costPrice) {
        this.name = brand + " " + category + " " + model + " " + year + " " + specification;
        this.brand = brand;
        this.category = category;
        this.model = model;
        this.year = year;
        this.specification = specification;
        this.salesPrice = salesPrice;
        this.costPrice = costPrice;

        this.state = ProductState.DEFECTIVE;
    }

    public String getName() {
        return this.name;
    }

    public double getSalesPrice() {
        return this.salesPrice;
    }

    public double getCostPrice() {return this.costPrice;}

    public double getProfitSum(){return this.salesPrice - this.costPrice;}
    //returns profit margin in percent
    public double getProfitMargin(){return (1 - ( this.costPrice / this.salesPrice)) * 100;}

    public boolean setCostPrice(int costPrice) {
        if (costPrice >= 0) {
            this.costPrice = costPrice;
            return true;
        }
        else {
            return false;

        }

    }
    public boolean addSparePart(SparePart sp) {
        return this.spareParts.add(sp);
    }

    public String getBrand() {
        return this.brand;
    }

    public String getCategory() {
        return this.category.toString();
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

    public void setState(ProductState state) {
        this.state = state;
    }
}