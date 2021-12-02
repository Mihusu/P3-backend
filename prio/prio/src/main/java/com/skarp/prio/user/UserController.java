package com.skarp.prio.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UserController implements UserDetailsService{
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         com.skarp.prio.user.User user = repository.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List authorities = Arrays.asList(new SimpleGrantedAuthority("user"));

        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}



/*package com.skarp.prio.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin("*")
@RestController
public class UserController {

    @Autowired
    MongoOperations operations;

    @Autowired
    UserRepository repository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping( "/hello" )
    public String hello() {return "Hello w"; }

    @RequestParam

    @PostMapping("/auth")
    public ResponseEntity<?> userAuth(
            @RequestParam("Username") String username,
            @RequestParam("Password") String password
    ) {
        if(User.getUsername() == username && User.getPassword() == password){
            return new  ResponseEntity<>("Logged in", HttpStatus.ACCEPTED);
        }else{
        return new  ResponseEntity<>("Bad credentials", HttpStatus.NOT_ACCEPTABLE);
    }
    }
}
*/
