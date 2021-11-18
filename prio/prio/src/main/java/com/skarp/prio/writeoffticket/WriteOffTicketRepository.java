package com.skarp.prio.writeoffticket;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WriteOffTicketRepository extends MongoRepository<WriteOffTicket, String> {

    //Do stuff....s
}
