package com.skarp.prio.spareparts;

import com.skarp.prio.products.Product;
import com.skarp.prio.spareparts.Enums.SparePartState;
import com.skarp.prio.spareparts.Enums.SparePartType;

public class UsedSparePart extends SparePart {
    private String origin_productId;     // productId of the write-off product that the spare-part is in
    // private Product origin;

    public UsedSparePart(Product origin, SparePartType type, double costPrice) {
        super(origin.getBrand(), origin.getCategory(), origin.getModel(), origin.getYear(), type, costPrice);
        this.origin_productId = origin.getProductId();
        // this.origin = origin;
        this.setState(SparePartState.AVAILABLE);
    }

    public String getOriginProductId() {
        return this.origin_productId;
    }

    public void setOriginProductId(String origin_productId) {
        this.origin_productId = origin_productId;
    }
}
