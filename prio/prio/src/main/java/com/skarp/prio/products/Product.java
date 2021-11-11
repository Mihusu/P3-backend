package com.skarp.prio.products;

import com.skarp.prio.spareparts.SparePart;

import java.util.ArrayList;

public class Product {
    private ArrayList<SparePart> spareParts = new ArrayList<>();
    private String productName;
    private String modelName;       // Ex: Pro, E480, 8, 9, 11 Pro
    private String modelYear;   // 2016
    private String brand;       // Apple, Lenovo
    private String category;    // Smartphone (and iPhone), Laptop, MacBook
    private String specification; //Ex. 128gb, white
    private final double salesPrice;
    private double costPrice;


    public Product(String brand, String category, String modelName, String modelYear, String specification, int salesPrice, int costPrice) {
        this.productName = brand + " " + category + " " + modelName + " " + modelYear + " " + specification;
        this.brand = brand;
        this.category = category;
        this.modelName = modelName;
        this.modelYear = modelYear;
        this.specification = specification;
        this.salesPrice = salesPrice;
        this.costPrice = costPrice;
    };

    public String getProductName() {
        return this.productName;
    }

    public double getSalesPrice() {
        return this.salesPrice;
    }

    public double getCostPrice() {return this.costPrice;}

    public double getProfitSum(){return this.salesPrice - this.costPrice;}
    //returns profit margin in procent
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
        return this.category;
    }

    public String getModelName() {
        return this.modelName;
    }

    public String getModelYear() {
        return this.modelYear;
    }
}