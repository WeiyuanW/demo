package com.example.demo.controller;

import com.example.demo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        return new ResponseEntity<User>(new User(userId, "David"), HttpStatus.valueOf(200));
    }
    // read Userid
    // insert (RequestBody)
    // delete Userid
    //Update  userID + new ReuquestBody User
}
