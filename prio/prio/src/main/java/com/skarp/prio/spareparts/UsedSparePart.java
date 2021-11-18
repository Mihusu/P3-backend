package com.skarp.prio.spareparts;

import com.skarp.prio.products.Product;
import com.skarp.prio.spareparts.Enums.UsedSparePartState;

public class UsedSparePart extends SparePart {

    private Product origin;     // the write-off product that the spare-part is in/came from
    private UsedSparePartState state;

    public UsedSparePart(Product origin, String grade, String type) {
        super(origin.getBrand(), origin.getCategory(), origin.getModelName(), origin.getModelYear(), grade, type);
        this.origin = origin;
        this.state = UsedSparePartState.AVAILABLE;
    }

    public Product getOrigin() {
        return this.origin;
    }

    public UsedSparePartState getState() {
        return this.state;
    }
}
