package com.skarp.prio.spareparts;

import com.skarp.prio.spareparts.Enums.NewSparePartState;

public class NewSparePart extends SparePart{

    private String location;    // Workshop location
    private String SKU;         // stock keeping unit
    private String state;       // INCOMING, ON-ORDER

    public NewSparePart(String brand, String category, String model, String modelYear, String grade, String type) {
        super(brand, category, model, modelYear, grade, type);
    }


    public String getLocation(){return this.location;};
    public String getSKU(){return this.SKU;};
    public String getState(){return this.state;};

}
