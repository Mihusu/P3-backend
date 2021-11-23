package com.skarp.prio.products;

import com.skarp.prio.Category;

public class ProductParser {
    public static Product parse(String line) {
        String[] line_split = line.split(";");

        Product parsedProduct = new Product();
        parsedProduct.setItemID(line_split[0]);
        parsedProduct.setName(line_split[4] +" "+ line_split[5] +" "+ line_split[2] +" "+ line_split[3]);
        parsedProduct.setSerialnumber(line_split[1]);
        parsedProduct.setModel(line_split[2]);
        parsedProduct.setSpecification(line_split[3]);
        parsedProduct.setBrand(line_split[4]);
        parsedProduct.setCategory(Category.valueOf(line_split[5]));
        parsedProduct.setState(ProductState.valueOf(line_split[6]));
        parsedProduct.setSalesPrice(Double.parseDouble(line_split[7].replace(",",".")));
        parsedProduct.setCostPrice(Double.parseDouble(line_split[8].replace(",",".")));

        return parsedProduct;
    }
}
