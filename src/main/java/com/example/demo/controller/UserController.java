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

/**
 * User Controller demonstrating three different data access patterns
 *
 * This controller has three sets of endpoints:
 * 1. /v1/api/jdbc/user/*      - Uses JDBC with JdbcTemplate
 * 2. /v1/api/hibernate/user/* - Uses Hibernate with EntityManager
 * 3. /v1/api/jpa/user/*       - Uses Spring Data JPA
 *
 * All three approaches follow the same Controller -> Service -> DAO pattern
 * but differ in how they interact with the database.
 */
@RestController
@RequestMapping("/v1/api")
@Validated
public class UserController {

    private final UserService jdbcUserService;
    private final UserService hibernateUserService;
    private final UserService jpaUserService;

    public UserController(
            @Qualifier("userServiceJdbc") UserService jdbcUserService,
            @Qualifier("userServiceHibernate") UserService hibernateUserService,
            @Qualifier("userServiceJpa") UserService jpaUserService) {
        this.jdbcUserService = jdbcUserService;
        this.hibernateUserService = hibernateUserService;
        this.jpaUserService = jpaUserService;
    }

    // ==================== JDBC ENDPOINTS ====================

    @GetMapping("/jdbc/user/{userId}")
    public ResponseEntity<User> getJdbcUserById(@PathVariable Long userId) {
        return jdbcUserService.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/jdbc/users")
    public ResponseEntity<List<User>> getAllJdbcUsers() {
        return ResponseEntity.ok(jdbcUserService.getAllUsers());
    }

    @PostMapping("/jdbc/user")
    public ResponseEntity<User> createJdbcUser(@Valid @RequestBody User user) {
        User createdUser = jdbcUserService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/jdbc/user/{userId}")
    public ResponseEntity<User> updateJdbcUser(@PathVariable Long userId, @Valid @RequestBody User user) {
        user.setId(userId);
        try {
            User updatedUser = jdbcUserService.updateUser(user);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/jdbc/user/{userId}")
    public ResponseEntity<Void> deleteJdbcUser(@PathVariable Long userId) {
        try {
            jdbcUserService.deleteUser(userId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ==================== HIBERNATE ENDPOINTS ====================

    @GetMapping("/hibernate/user/{userId}")
    public ResponseEntity<User> getHibernateUserById(@PathVariable Long userId) {
        return hibernateUserService.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/hibernate/users")
    public ResponseEntity<List<User>> getAllHibernateUsers() {
        return ResponseEntity.ok(hibernateUserService.getAllUsers());
    }

    @PostMapping("/hibernate/user")
    public ResponseEntity<User> createHibernateUser(@Valid @RequestBody User user) {
        User createdUser = hibernateUserService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/hibernate/user/{userId}")
    public ResponseEntity<User> updateHibernateUser(@PathVariable Long userId, @Valid @RequestBody User user) {
        user.setId(userId);
        try {
            User updatedUser = hibernateUserService.updateUser(user);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/hibernate/user/{userId}")
    public ResponseEntity<Void> deleteHibernateUser(@PathVariable Long userId) {
        try {
            hibernateUserService.deleteUser(userId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ==================== SPRING DATA JPA ENDPOINTS ====================

    @GetMapping("/jpa/user/{userId}")
    public ResponseEntity<User> getJpaUserById(@PathVariable Long userId) {
        return jpaUserService.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/jpa/users")
    public ResponseEntity<List<User>> getAllJpaUsers() {
        return ResponseEntity.ok(jpaUserService.getAllUsers());
    }

    @PostMapping("/jpa/user")
    public ResponseEntity<User> createJpaUser(@Valid @RequestBody User user) {
        User createdUser = jpaUserService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/jpa/user/{userId}")
    public ResponseEntity<User> updateJpaUser(@PathVariable Long userId, @Valid @RequestBody User user) {
        user.setId(userId);
        try {
            User updatedUser = jpaUserService.updateUser(user);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/jpa/user/{userId}")
    public ResponseEntity<Void> deleteJpaUser(@PathVariable Long userId) {
        try {
            jpaUserService.deleteUser(userId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
