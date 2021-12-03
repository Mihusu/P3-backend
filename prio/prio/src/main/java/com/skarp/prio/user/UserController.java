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


            // Find users with matching username
            User requestUser = operations.findOne(userQuery, User.class);

            if (requestUser != null) {
                // Check user password against input string
                if (requestUser.checkPassword(password)) {
                    requestUser.compoundCounter();
                    String cookie = SHA3.hashPassword(requestUser.getCounter()+requestUser.getId());
                    requestUser.setSessionCookie(cookie);
                    repository.save(requestUser);
                    return new ResponseEntity<>(cookie, HttpStatus.ACCEPTED);
                } else {
                    return new ResponseEntity<>("Password didn't match ", HttpStatus.FORBIDDEN);
                }
            } else {
                return new ResponseEntity<>("No such user", HttpStatus.NOT_FOUND);
            }
        }


    @PostMapping("/auth/validateSessionCookie")
    public ResponseEntity<?> authenticateSessionCookie(
            @RequestParam(value="username") String username,
            @RequestParam(value="sessionCookie") String sessionCookie) {

        Query userQuery = new Query();

        if (username != null) { userQuery.addCriteria(Criteria.where("username").is(username)); }

        try {
            // Find users with matching username
            User requestUser = operations.findOne(userQuery, User.class);

            if (requestUser != null) {
                // Check the sessionCookie against calculating what should be the correct sessionCookie
                if (sessionCookie.equals(SHA3.hashPassword(requestUser.getCounter()+requestUser.getId()))) {
                    return new ResponseEntity<>("Valid Cookie", HttpStatus.ACCEPTED);
                } else {
                    return new ResponseEntity<>("Invalid Cookie", HttpStatus.FORBIDDEN);
                }
            } else {
                return new ResponseEntity<>("No such user", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestParam(value="username") String username,
            @RequestParam(value="password") String password) {

        try {
            //check if the username is filled out
            if (username.split("").length >= 4) {
                //check if the username is already taken
                Query userQuery = new Query();
                userQuery = userQuery.addCriteria(Criteria.where("username").is(username));
                if(!operations.exists(userQuery, User.class)){
                    //check if the password livees up to the security requirements
                    if(password.split("").length > 6){
                        User user = new User(username, password);
                        repository.save(user);
                        return new ResponseEntity<>("User registered", HttpStatus.ACCEPTED);
                    }else{return new ResponseEntity<>("Password to short", HttpStatus.NOT_ACCEPTABLE);}
                }else{ return new ResponseEntity<>("Username already taken", HttpStatus.SERVICE_UNAVAILABLE); }
            }else{ return new ResponseEntity<>("Username to short", HttpStatus.NOT_ACCEPTABLE);}
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
    /*
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestParam(value="username") String username,
            @RequestParam(value="password") String password) {
        try {
            Query userQuery = new Query();

            //if username is not null
            if (username != null) {
                userQuery.addCriteria(Criteria.where("username").is(username));

                //then Check if the username is legal, not legal if it already exists
                User requestUser = operations.findOne(userQuery, User.class);
                if (!requestUser.getUsername().equals(username)) {
                    //check if the password is acceptable
                    if (password.split("").length < 8) {
                        User user = new User(username, password);
                        repository.save(user);
                        return new ResponseEntity<>("User registered", HttpStatus.ACCEPTED);
                    } else {
                        return new ResponseEntity<>("Password to short", HttpStatus.NOT_ACCEPTABLE);
                    }
                } else {
                    return new ResponseEntity<>("Username already taken", HttpStatus.NOT_ACCEPTABLE);
                }
            }
        }catch(Exception e){
            return new ResponseEntity<>(e, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
     */
}


