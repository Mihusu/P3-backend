package com.skarp.prio.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface UserRepositoryCustom{
    User findByUsername(String username);

}