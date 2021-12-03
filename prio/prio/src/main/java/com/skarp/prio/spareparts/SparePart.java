package com.skarp.prio.spareparts;

import com.skarp.prio.products.Category;
import com.skarp.prio.spareparts.Enums.SparePartState;
import com.skarp.prio.spareparts.Enums.SparePartType;
import org.springframework.data.annotation.Id;

import java.util.Date;

public class SparePart {
    
    @Id
    private String part_id;         // Internal ID use
    protected String name;          // Brand-Category-Model-Grade-Type
    private String brand;           // Apple, Lenovo
    private Category category;      // Smartphone (and iPhone), Laptop, MacBook
    private String model;           // Ex: Pro, E480
    private SparePartType type;     // Ex: Battery, Screen
    private Date addedDate;         // Date of creation
    private double costPrice;       // Cost price
    private SparePartState state;   // AVAILABLE, RESERVED, CONSUMED, ON_ORDER, INCOMING, MARKED_FUNCTIONAL

    public SparePart(String brand, Category category, String model, SparePartType type, double costPrice) {
        this.name = this.setDisplayName(brand, category, model, type);
        this.brand = brand.toUpperCase();
        this.category = category;
        this.model = model.toUpperCase();
        this.type = type;
        this.costPrice = costPrice;
        this.addedDate = new Date();
    }

    public SparePart() {
    }

    public String setDisplayName(String brand, Category category, String model, SparePartType type) {

        String displayName = "";

        if (brand != null){displayName = displayName.concat(brand + " ");}
        if (category != null){displayName = displayName.concat(category + " ");}
        if (model != null){displayName = displayName.concat(model + " ");}
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

    public void setPart_id(String part_id) {
        this.part_id = part_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setType(SparePartType type) {
        this.type = type;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }
}
