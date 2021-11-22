package com.skarp.prio.spareparts;

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
    private String grade;       // OEM, slightly used
    private double cost;

    public SparePart(String brand, String category, String model, String modelYear, String grade, String type) {
        this.name = brand+" "+category+" "+model+"-"+modelYear+": "+grade+"-"+type;
        this.brand = brand;
        this.category = category;
        this.model = model;
        this.modelYear = modelYear;
        this.grade = grade;
        this.type = type;
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

    public String getGrade() {
        return grade;
    }

}
