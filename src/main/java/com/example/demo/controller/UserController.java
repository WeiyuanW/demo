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

import java.util.List;

@RestController
@RequestMapping("/v1/api")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(@Qualifier("userServiceImpl") UserService userService) {
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
        return new ResponseEntity<User>(userService.getUserById(userId), HttpStatus.valueOf(200));
    }

    // insert (RequestBody)
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        // logic to save the user
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    //Update  userID + new ReuquestBody User
    @PutMapping("/user")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        user.setId(user.getId());
        return ResponseEntity.ok(userService.saveUser(user)); // new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // delete Userid
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build(); // new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

}
