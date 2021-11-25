package com.skarp.prio.spareparts;

import com.skarp.prio.products.Product;
import com.skarp.prio.spareparts.Enums.Grade;
import com.skarp.prio.spareparts.Enums.SparePartState;

public class UsedSparePart extends SparePart {

    private Product origin;     // the write-off product that the spare-part is in/came from

    public UsedSparePart(Product origin, Grade grade, String type, double costPrice) {
        super(origin.getBrand(), origin.getCategory(), origin.getModel(), origin.getYear(), grade, type, costPrice);
        this.origin = origin;
        this.setState(SparePartState.AVAILABLE);
    }

    public Product getOrigin() {
        return this.origin;
    }

}
