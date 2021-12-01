package com.skarp.prio.spareparts;

import com.skarp.prio.products.Category;
import com.skarp.prio.spareparts.Enums.Grade;
import com.skarp.prio.spareparts.Enums.SparePartType;

public class NewSparePart extends SparePart{

    private String location;    // Workshop location
    private String SKU;         // stock keeping unit
    private Grade grade;        // OEM, ORIGINAL, A

    public NewSparePart(String brand, Category category, String model, String modelYear, Grade grade, SparePartType type, double costPrice) {
        super(brand, category, model, modelYear, type, costPrice);
        this.grade = grade;
        name = name.concat(grade.toString()); // Todo: may need super.name, awaiting test
    }

    public String getLocation(){return this.location;}
    public String getSKU(){return this.SKU;}
    public void setLocation(String location){this.location = location;}
    public void setSKU(String SKU){this.SKU = SKU;}

}
