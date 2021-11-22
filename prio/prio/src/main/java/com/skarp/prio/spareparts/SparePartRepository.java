package com.skarp.prio.spareparts;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SparePartRepository extends MongoRepository<SparePart, String> {

}
