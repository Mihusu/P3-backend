package com.skarp.prio.products;

import com.skarp.prio.spareparts.SparePart;

import java.util.ArrayList;

public class Product {
    private ArrayList<SparePart> spareParts = new ArrayList<>();
    private String productName;
    private final double salesPrice;
    private double costPrice;


    public Product(String productName, int salesPrice, int costPrice) {
        this.productName = productName;
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
}