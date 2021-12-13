package com.skarp.prio.products;

public class ProductParser {
    public static Product parse(String line) {
        String[] line_split = line.split(";");

        Product parsedProduct = new Product();
        parsedProduct.setProductId(line_split[0].trim());
        parsedProduct.setName(line_split[4] +" "
                            + line_split[5].trim().toUpperCase() +" "
                            + line_split[2].replace("''","\"") +" "
                            + line_split[3].trim());
        parsedProduct.setSerialnumber(line_split[1]);
        parsedProduct.setModel(line_split[2].replace("''","\""));
        parsedProduct.setSpecification(line_split[3]);
        parsedProduct.setBrand(line_split[4]);
        parsedProduct.setState(ProductState.valueOf(line_split[6].trim().toUpperCase()));
        parsedProduct.setSalesPrice(Double.parseDouble(line_split[7].replace(",",".")));
        parsedProduct.setCostPrice(Double.parseDouble(line_split[8].replace(",",".")));
        parsedProduct.setCategory(Category.valueOf(line_split[5].trim().toUpperCase()));
        parsedProduct.setDefectiveComment(line_split[9].trim());

        return parsedProduct;
    }
}
