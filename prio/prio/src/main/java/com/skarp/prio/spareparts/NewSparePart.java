package com.skarp.prio.spareparts;

import com.skarp.prio.spareparts.Enums.Grade;

public class NewSparePart extends SparePart{

    private String location;    // Workshop location
    private String SKU;         // stock keeping unit
    private String state;       // INCOMING, ON-ORDER

    public NewSparePart(String brand, String category, String model, String modelYear, Grade grade, String type, double costPrice) {
        super(brand, category, model, modelYear, grade, type, costPrice);
    }


    public String getLocation(){return this.location;};
    public String getSKU(){return this.SKU;};

}
