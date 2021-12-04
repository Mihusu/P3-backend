package com.skarp.prio.spareparts;

import com.skarp.prio.products.Category;
import com.skarp.prio.spareparts.Enums.Grade;
import com.skarp.prio.spareparts.Enums.SparePartType;

public class NewSparePart extends SparePart{

    private String location;    // Workshop location
    private String sku;         // stock keeping unit
    private Grade grade;        // OEM, ORIGINAL, A

    public NewSparePart(String brand, Category category, String model, Grade grade, SparePartType type, double costPrice, String sku) {
        super(brand.toUpperCase(), category, model.toUpperCase(), type, costPrice);
        this.grade = grade;
        this.sku = sku;
        name = name.concat(" " + grade.toString()); // Todo: may need super.name, awaiting test
    }

    public NewSparePart() {
    }

    public String getLocation(){return this.location;}
    public String getSku(){return this.sku;}
    public void setLocation(String location){this.location = location;}
    public void setSku(String sku){this.sku = sku;}
    public void setGrade(Grade grade) {this.grade = grade;}
    public Grade getGrade() {return this.grade;}
}
