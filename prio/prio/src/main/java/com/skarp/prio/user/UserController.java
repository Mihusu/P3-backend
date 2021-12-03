package com.skarp.prio.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
public class UserController {

    @Autowired
    MongoOperations operations;

    @Autowired
    UserRepository repository;

    @GetMapping("/users")
    public List<User> user(
            @RequestParam(required=false, value="username") String username,
            @RequestParam(required=false, value="password") String password
    ){
        Query userQuery = new Query();
        if (username != null) {userQuery.addCriteria(Criteria.where("username").regex(username));}
        if (password != null) {userQuery.addCriteria(Criteria.where("password").regex(password));}

        // Find users matching Query
        try {
            return operations.find(userQuery, User.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @PostMapping("/auth")
    public ResponseEntity<?> authenticateUser(
            @RequestParam(value="username") String username,
            @RequestParam(value="password") String password) {

        Query userQuery = new Query();

        if (username != null) { userQuery.addCriteria(Criteria.where("username").is(username)); }

        try {
            // Find users with matching username
            User requestUser = operations.findOne(userQuery, User.class);

            if (requestUser != null) {
                // Check user password against input string
                if (requestUser.checkPassword(password)) {
                    requestUser.compoundCounter();
                    return new ResponseEntity<>(SHA3.hashPassword(requestUser.getCounter()+requestUser.getId()), HttpStatus.ACCEPTED);
                } else {
                    return new ResponseEntity<>("Password didn't match ", HttpStatus.FORBIDDEN);
                }
            } else {
                return new ResponseEntity<>("No such user", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}


