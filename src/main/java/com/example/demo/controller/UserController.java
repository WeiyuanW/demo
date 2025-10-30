package com.example.demo.controller;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(@Qualifier("userServiceImpl1") UserService userService) {
        this.userService = userService;
    }

    // read Userid
    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getUserById(@Valid @PathVariable Long userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("UserId must be > 0");
        }
        if (userId == 99) {
            throw new UserNotFoundException("User not found");
        }

//        userService.getUser(userId);
        return new ResponseEntity<User>(new User(userId, "David"), HttpStatus.valueOf(200));
    }

    // insert (RequestBody)
    @PostMapping(value="/user")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        // logic to save the user
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // delete Userid
    @DeleteMapping("user/{userId}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long userId) {
        // delete user from DB
        return ResponseEntity.noContent().build(); // new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    //Update  userID + new ReuquestBody User
    @PutMapping("user/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User user) {
        // fetch user from DB, modify fields, then save
        User updatedUser = new User(userId, user.getFirstName());
        return ResponseEntity.ok(updatedUser); // new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

}
