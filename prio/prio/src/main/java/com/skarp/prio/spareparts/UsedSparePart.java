package com.skarp.prio.spareparts;

import com.skarp.prio.products.Product;

public class UsedSparePart extends SparePart {

    private Product origin;     // the write-off product that the spare-part is in/came from

    public UsedSparePart(Product origin, String grade, String type) {
        super(origin.getBrand(), origin.getCategory(), origin.getModelName(), origin.getModelYear(), grade, type);
        this.origin = origin;

    }

    public Product getOrigin() {
        return this.origin;
    }
}
