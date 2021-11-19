package com.skarp.prio.repairs;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairRepository extends MongoRepository<Repair, String> {

    //Do something ...

}
