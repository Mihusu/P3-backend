package com.skarp.prio.spareparts;

import com.skarp.prio.products.Category;
import com.skarp.prio.spareparts.Enums.Grade;

public class SparePartDescriptor {

    private String SKU;             // A12icpre, A12bat-TPH
    private String name;            // Brand-Category-Model-Year-Specification-Grade-Type
    private String type;            // Battery, Screen
    private String model;           // Pro, E480
    private String modelYear;       // 2016
    private String brand;           // Apple, Lenovo
    private Category category;        // Smartphone (and iPhone), Laptop, MacBook
    private String specification;   // Black, White, 128GB
    private Grade grade;           // OEM, Original, A++
    private int qty;

    public SparePartDescriptor(String brand, Category category, String model, String modelYear, String specification, Grade grade, String type, String SKU) {

        this.brand = brand;
        this.category = category;
        this.model = model;
        this.modelYear = modelYear;
        this.grade = grade;
        this.type = type;
        this.specification = specification;
        this.SKU = SKU;
        this.qty = 0;
        this.name = brand+" "+category+" "+model+"-"+modelYear+ " " + specification + ": "+grade+"-"+type;
    }

}
