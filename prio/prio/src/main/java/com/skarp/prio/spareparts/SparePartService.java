package com.skarp.prio.spareparts;

import com.skarp.prio.products.Category;
import com.skarp.prio.products.Product;
import com.skarp.prio.repairs.Repair;
import com.skarp.prio.spareparts.Enums.SparePartState;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface SparePartService {

    List<SparePart> getSparePartList(@RequestParam(required = false, value = "category") Category category,
                                     @RequestParam(required = false, value = "model") String model,
                                     @RequestParam(required = false, value = "year") String year,
                                     @RequestParam(required = false, value = "brand") String brand,
                                     @RequestParam(required = false, value = "state") SparePartState state);

    SparePart getSparePartByID(@PathVariable(required = true, value = "id") String id);
    List<SparePart> getRecommendedSpareParts(Repair repair);
}
