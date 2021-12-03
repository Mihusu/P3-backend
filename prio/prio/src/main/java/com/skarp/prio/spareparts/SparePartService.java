package com.skarp.prio.spareparts;

import com.skarp.prio.products.Category;
import com.skarp.prio.repairs.Repair;
import com.skarp.prio.spareparts.Enums.SparePartState;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface SparePartService {
/*
    List<SparePart> getSparePartList( Todo: why dese not necessary
      @RequestParam(required = false, value = "brand") String brand,
      @RequestParam(required = false, value = "category") Category category,
      @RequestParam(required = false, value = "model") String model,
      @RequestParam(required = false, value = "year") String modelYear,
      @RequestParam(required = false, value = "state") SparePartState state);
*/
    List<SparePart> getSparePartList(String name, String brand, String category, String model, String type, String state, String sortBy);

    SparePart getSparePartByID(@PathVariable(value = "id") String id);
    List<SparePart> getRecommendedSpareParts(Repair repair);

    SparePart uploadSparePart(
      @RequestParam("brand") String brand,
      @RequestParam("category") String category,
      @RequestParam("model") String model,
      @RequestParam("modelYear") String modelYear,
      @RequestParam("grade") String grade,
      @RequestParam("type") String type,
      @RequestParam("cost_price") Double costPrice,
      @RequestParam("sku") String sku);
}
