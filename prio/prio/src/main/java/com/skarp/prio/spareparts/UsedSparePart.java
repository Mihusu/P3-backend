package com.skarp.prio.spareparts;

import com.skarp.prio.products.Category;
import com.skarp.prio.products.Product;
import com.skarp.prio.spareparts.Enums.SparePartState;
import com.skarp.prio.spareparts.Enums.SparePartType;

public class UsedSparePart extends SparePart {
    private String origin_productId;     // productId of the write-off product that the spare-part is in
    // private Product origin;

    public UsedSparePart(String originProductId, String brand, Category category, String model, String modelYear, SparePartType type, double costPrice) {
        super(brand, category, model, modelYear, type, costPrice);
        this.originProductId = originProductId;
    }

    public String getOriginProductId() {
        return this.originProductId;
    }

    public void setOriginProductId(String origin_productId) {
        this.origin_productId = origin_productId;
    }
}
