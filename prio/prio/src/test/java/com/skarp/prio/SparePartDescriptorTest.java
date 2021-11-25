package com.skarp.prio;

import com.skarp.prio.products.Category;
import com.skarp.prio.spareparts.Enums.Grade;
import com.skarp.prio.spareparts.SparePartDescriptor;
import org.junit.jupiter.api.Test;

public class SparePartDescriptorTest {

    @Test
    public void canAddNewDescriptor() {

        SparePartDescriptor descriptor = new SparePartDescriptor("Apple", Category.iphone, "11 Pro", "2018", "128 gb white", Grade.OEM, "Battery", "23345");

    }
}
