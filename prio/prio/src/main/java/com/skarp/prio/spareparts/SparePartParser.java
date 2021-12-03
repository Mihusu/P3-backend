package com.skarp.prio.spareparts;

import com.skarp.prio.products.Category;
import com.skarp.prio.spareparts.Enums.SparePartState;
import com.skarp.prio.spareparts.Enums.SparePartType;

import java.util.Date;

public class SparePartParser {
    public static NewSparePart parse(String line) {
        String[] line_split = line.split(";");

        NewSparePart parsedSparePart = new NewSparePart();
        parsedSparePart.setSku(line_split[0].trim());
        parsedSparePart.setPart_id(line_split[1].trim());
        parsedSparePart.setBrand(line_split[2].trim());
        parsedSparePart.setCategory(Category.valueOf(line_split[3].trim().toUpperCase()));
        parsedSparePart.setModel(line_split[4]);
        parsedSparePart.setType(SparePartType.valueOf(line_split[5].trim().toUpperCase()));
        parsedSparePart.setCostPrice(Double.parseDouble(line_split[6].replace(",", ".")));
        parsedSparePart.setState(SparePartState.AVAILABLE);
        parsedSparePart.setAddedDate(new Date());

        return parsedSparePart;
    }
}
