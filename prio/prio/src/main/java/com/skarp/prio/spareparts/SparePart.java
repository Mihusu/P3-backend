package com.skarp.prio.spareparts;

import com.skarp.prio.products.Category;
import com.skarp.prio.spareparts.Enums.SparePartState;
import com.skarp.prio.spareparts.Enums.SparePartType;
import org.springframework.data.annotation.Id;

import java.util.Date;

public class SparePart {
    
    @Id
    private String part_id;         // Internal ID use
    protected String name;          // Brand-Category-Model-Year-Grade-Type
    private String brand;           // Apple, Lenovo Todo: Decide whether this should be String or Category
    private Category category;      // Smartphone (and iPhone), Laptop, MacBook
    private String model;           // Ex: Pro, E480
    private String modelYear;       // 2016
    private SparePartType type;     // Ex: Battery, Screen
    private Date addedDate;         // Date of creation
    private double costPrice;       // Cost price
    private SparePartState state;   // AVAILABLE, RESERVED, CONSUMED, ON_ORDER, INCOMING, MARKED_FUNCTIONAL

    public SparePart(String brand, Category category, String model, String modelYear, SparePartType type, double costPrice) {
        this.name = this.setDisplayName(brand, category, model, modelYear, type);
        this.brand = brand;
        this.category = category;
        this.model = model;
        this.modelYear = modelYear != null ?  modelYear : "";
        this.type = type;
        this.costPrice = costPrice;
        this.addedDate = new Date();
    }

    public String setDisplayName(String brand, Category category, String model, String modelYear, SparePartType type) {

        String displayName = "";

        if (brand != null){displayName = displayName.concat(brand + " ");}
        if (category != null){displayName = displayName.concat(category + " ");}
        if (model != null){displayName = displayName.concat(model + " ");}
        if (modelYear != null){displayName = displayName.concat(modelYear + " ");}
        if (type != null){displayName = displayName.concat(type.toString());}

        return displayName;
    }
    public String getPart_id() {
        return part_id;
    }

    public String getName() {
        return name;
    }

    public SparePartType getType() {
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

    public Category getCategory() {
        return category;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public double getCostPrice() {return costPrice;}

    public SparePartState getState() {return state;}

    public void setState(SparePartState state) {this.state = state;}

}
