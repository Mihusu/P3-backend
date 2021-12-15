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
public class
UserController {

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
                    repository.save(requestUser);
                    return new ResponseEntity<>(cookie, HttpStatus.ACCEPTED);
                } else {
                    return new ResponseEntity<>("Password didn't match ", HttpStatus.FORBIDDEN);
                }
            } else {
                return new ResponseEntity<>("No such user", HttpStatus.NOT_FOUND);
            }
        }


    @PostMapping("/auth/validate_session_cookie")
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
                if (sessionCookie.equals(SHA3.hashPassword(requestUser.getCounter()+requestUser.getId())) && !(requestUser.getUserPrivilege().equals(UserPrivilege.UNASSIGNED))) {
                    return new ResponseEntity<>("Valid Cookie", HttpStatus.OK);
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

    @PostMapping("/users/setprivilege")
    public ResponseEntity<?> changeUserPrivilege(
            @RequestParam(value="username") String username,
            @RequestParam(value="userPrivilege") String userPrivilege) {
        Query userQuery = new Query();

        if (username != null) { userQuery.addCriteria(Criteria.where("username").is(username)); }


        // Find users with matching username
        User requestUser = operations.findOne(userQuery, User.class);

        if (requestUser != null) {
            // Check user password against input string
            if(userPrivilege.equals("FULL_ACCESS")) {
                requestUser.setUserPrivilege(UserPrivilege.FULL_ACCESS);
                requestUser.resetDateResigned();
                repository.save(requestUser);
                return new ResponseEntity<>("User Privilege changed to FULL_ACCESS", HttpStatus.ACCEPTED);
            }else if(userPrivilege.equals("SEMI_ACCESS")) {
                requestUser.setUserPrivilege(UserPrivilege.SEMI_ACCESS);
                requestUser.resetDateResigned();
                repository.save(requestUser);
                return new ResponseEntity<>("User Privilege changed to SEMI_ACCESS", HttpStatus.ACCEPTED);
            }else if(userPrivilege.equals("UNASSIGNED")) {
                requestUser.setUserPrivilege(UserPrivilege.UNASSIGNED);
                repository.save(requestUser);
                return new ResponseEntity<>("User Privilege changed to UNASSIGNED", HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>("No such user privilege", HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>("No such user", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/users/resign")
    public ResponseEntity<?> changeUserPrivilege(
            @RequestParam(value="username") String username) {
        Query userQuery = new Query();

        if (username != null) { userQuery.addCriteria(Criteria.where("username").is(username)); }


        // Find users with matching username
        User requestUser = operations.findOne(userQuery, User.class);

        if (requestUser != null) {
            // Check user password against input string
            if(requestUser.getDateResigned() == null) {
                requestUser.resign();
                repository.save(requestUser);
                return new ResponseEntity<>("Resigned user", HttpStatus.ACCEPTED);
            }else {
                return new ResponseEntity<>("User already resigned", HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>("No such user", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/users/setpassword")
    public ResponseEntity<?> changePassword(
            @RequestParam(value="username") String username,
            @RequestParam(value="password") String password){
        Query userQuery = new Query();

        if (username != null) { userQuery.addCriteria(Criteria.where("username").is(username)); }

        // Find users with matching username
        User requestUser = operations.findOne(userQuery, User.class);

        if (requestUser != null) {
            // Check user password against input string
            if(password.split("").length >= 6) {
                requestUser.setPassword(password);
                repository.save(requestUser);
                return new ResponseEntity<>("password changed", HttpStatus.ACCEPTED);
            }else {
                return new ResponseEntity<>("password to short", HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>("No such user", HttpStatus.NOT_FOUND);
        }
    }
}


