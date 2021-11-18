package com.skarp.prio.repairs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RepairController {

  @Autowired
  MongoOperations operations;

  @Autowired
  RepairRepository repairRepository;


}
