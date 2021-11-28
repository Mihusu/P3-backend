package com.skarp.prio.spareparts;

import com.skarp.prio.products.Category;
import com.skarp.prio.spareparts.Enums.SparePartState;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<SparePart> getSparePartList(Category category, String model, String year, String brand, SparePartState state) {


        Query sparePartQuery = new Query();

        // Check for Params and add to Criteria
        if (category != null) {sparePartQuery.addCriteria(Criteria.where("category").is(category));}
        if (model != null) {sparePartQuery.addCriteria(Criteria.where("model").is(model));}
        if (year != null) {sparePartQuery.addCriteria(Criteria.where("year").is(year));}
        if (brand != null) {sparePartQuery.addCriteria(Criteria.where("brand").is(brand));}
        if (state != null) {sparePartQuery.addCriteria(Criteria.where("state").is(state));}


        // Find NewSpareParts matching Query
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
}
