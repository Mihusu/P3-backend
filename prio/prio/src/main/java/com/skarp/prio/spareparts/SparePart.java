package com.skarp.prio.spareparts;

import com.skarp.prio.spareparts.Enums.Grade;
import com.skarp.prio.spareparts.Enums.SparePartState;

import java.util.Date;

public class SparePart {

    private int part_id;        // Internal ID use
    private String name;        // Brand-Category-Model-Year-Grade-Type
    private String type;        // Ex: Battery, Screen
    private String model;       // Ex: Pro, E480
    private String modelYear;   // 2016
    private String brand;       // Apple, Lenovo
    private String category;    // Smartphone (and iPhone), Laptop, MacBook
    private Date addedDate;
    private Grade grade;        // OEM, slightly used
    private double costPrice;


    private SparePartState state;

    public SparePart(String brand, String category, String model, String modelYear, Grade grade, String type, double costPrice) {
        this.name = brand+" "+category+" "+model+"-"+modelYear+": "+grade+"-"+type;
        this.brand = brand;
        this.category = category;
        this.model = model;
        this.modelYear = modelYear;
        this.grade = grade;
        this.type = type;
        this.costPrice = costPrice;
        this.addedDate = new Date();
    }


    public int getPart_id() {
        return part_id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getModel() {
        return model;
    }

    public String getModelYear() {
        return modelYear;
    }

    public String getBrand() {
        return brand;
    }

    public String getCategory() {
        return category;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public Grade getGrade() {
        return grade;
    }

    public double getCostPrice() {return costPrice;}

    public SparePartState getState() {return state;}

    public void setState(SparePartState state) {this.state = state;}

}
