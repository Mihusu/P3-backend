package com.skarp.prio.orderlist;

import com.skarp.prio.spareparts.NewSparePart;
import com.skarp.prio.spareparts.SparePartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class OrderListController {

  @Autowired
  MongoOperations operations;

  @Autowired
  SparePartRepository sparePartRepository;


  @GetMapping ("/orderlist")
  public List<NewSparePart> spareparts(
    @RequestParam(required = false, value="state") String state,
    @RequestParam(required = false, value="type") String type
  //  @RequestParam(required = false, value="sortBy") String sortBy
  ) {
    // Create Empty Query
    Query sparePartQuery = new Query();

    // Check for Params and add to Criteria
    if (state != null) {sparePartQuery.addCriteria(Criteria.where("state").is(state));}
    if (type != null) {sparePartQuery.addCriteria(Criteria.where("type").is(type));}
    //if (sortBy != null) {sparePartQuery.with(Sort.by(Sort.Direction.ASC, sortBy));}

    // Find NewSpareParts matching Query
    return operations.find(sparePartQuery, NewSparePart.class);
  }

}
