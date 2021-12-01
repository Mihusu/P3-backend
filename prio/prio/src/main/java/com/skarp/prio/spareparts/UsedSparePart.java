package com.skarp.prio.spareparts;

import com.skarp.prio.products.Category;
import com.skarp.prio.products.Product;
import com.skarp.prio.spareparts.Enums.SparePartState;
import com.skarp.prio.spareparts.Enums.SparePartType;

public class UsedSparePart extends SparePart {
    private String originProductId;     // productId of the write-off product that the spare-part is in

    public UsedSparePart(String originProductId, String brand, Category category, String model, String modelYear, SparePartType type, double costPrice) {
        super(brand, category, model, modelYear, type, costPrice);
        this.originProductId = originProductId;
    }

    public String getOriginProductId() {
        return this.originProductId;
    }

    public void setOriginProductId(String origin_productId) {
        this.originProductId = origin_productId;
    }
}
