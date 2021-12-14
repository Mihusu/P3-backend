package com.skarp.prio.spareparts;

import com.skarp.prio.products.Category;
import com.skarp.prio.products.Product;
import com.skarp.prio.repairs.Repair;
import com.skarp.prio.spareparts.Enums.Grade;
import com.skarp.prio.spareparts.Enums.SparePartState;
import com.skarp.prio.spareparts.Enums.SparePartType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SparePartServiceImpl implements SparePartService {

    @Autowired
    MongoOperations operations;

    @Autowired
    SparePartRepository sparePartRepository;


    @Override
    public List<SparePart> getSparePartList(String name, String brand, String category, String model, String type, String state, String sortBy) {

        Query sparePartQuery = new Query();

        // Check for Params and add to Criteria
        if (name != null) {sparePartQuery.addCriteria(Criteria.where("name").regex(name.toUpperCase()));}
        if (brand != null) {sparePartQuery.addCriteria(Criteria.where("brand").is(brand.toUpperCase()));}
        if (category != null) {sparePartQuery.addCriteria(Criteria.where("category").is(category.toUpperCase()));}
        if (model != null) {sparePartQuery.addCriteria(Criteria.where("model").is(model.toUpperCase()));}
        if (type != null) {sparePartQuery.addCriteria(Criteria.where("type").is(type.toUpperCase()));}
        if (state != null) {sparePartQuery.addCriteria(Criteria.where("state").is(state.toUpperCase()));}
        if (sortBy != null) {sparePartQuery.with(Sort.by(Sort.Direction.ASC, sortBy));}

        // Find SpareParts matching Query
        return operations.find(sparePartQuery, SparePart.class);
    }

    @Override
    public SparePart getSparePartByID(@PathVariable(value = "id") String id) {

        if (sparePartRepository.findById(id).isEmpty()) {
            String msg = "Sparepart not found with id: " + id;
            throw new NoSuchElementException(msg);
        }

        return sparePartRepository.findById(id).get();
    }

    /**
     * Gives back a sorted list of compatible & available sparepart types for a given repair
     * @param repair The repair to get recommended spareparts from
     * @return A list of compatible spareparts for the product under repair
     */
    @Override
    public List<SparePart> getRecommendedSpareParts(Repair repair) {

        Product product = repair.getProduct();

        Query query = new Query();
        if (product.getCategory() != null) {query.addCriteria(Criteria.where("category").is(product.getCategory()));}
        if (product.getModel() != null) {query.addCriteria(Criteria.where("model").is(product.getModel()));}
        if (product.getBrand() != null) {query.addCriteria(Criteria.where("brand").is(product.getBrand()));}
        query.addCriteria(Criteria.where("state").is(SparePartState.AVAILABLE));
        query.with(Sort.by(Sort.Direction.ASC, "type"));

        return operations.find(query, SparePart.class);
    }

    @Override
    public NewSparePart uploadSparePart(String brand, String category, String model, String grade, String type, Double costPrice, String sku) {

        // Transform input strings to enum types
        Category enumCategory = Category.valueOf(category.toUpperCase());
        System.out.println("enumCategory = " + enumCategory);

        Grade enumGrade = Grade.valueOf(grade.toUpperCase());
        System.out.println("enumGrade = " + enumGrade);

        SparePartType enumType = SparePartType.valueOf(type.toUpperCase());
        System.out.println("enumType = " + enumType);

        NewSparePart sparePart = new NewSparePart(brand, enumCategory, model, enumGrade, enumType, costPrice, sku);
        sparePart.setState(SparePartState.AVAILABLE);

        System.out.println("saving to db: " + sparePart);
        System.out.println("sparePart.getPart_id() = " + sparePart.getPart_id());
        sparePart = sparePartRepository.save(sparePart);
        System.out.println("saved to db: " + sparePart);
        System.out.println("sparePart.getPart_id() = " + sparePart.getPart_id());

        return sparePart;

    }
}
