package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserDaoJdbc;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for JDBC implementation
 *
 * The service layer is where business logic resides.
 * It acts as a bridge between the controller and the data access layer.
 * The @Transactional annotation ensures all operations within a method
 * happen in a single database transaction.
 */
@Service("userServiceJdbc")
@Transactional
public class UserServiceJdbc implements UserService {

    private final UserDaoJdbc userDaoJdbc;

    public UserServiceJdbc(UserDaoJdbc userDaoJdbc) {
        this.userDaoJdbc = userDaoJdbc;
    }

    @Override
    public User createUser(User user) {
        // Business logic can go here (validation, etc.)
        return userDaoJdbc.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) {
        return userDaoJdbc.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDaoJdbc.findAll();
    }

    @Override
    public User updateUser(User user) {
        // You could add logic here to check if user exists first
        if (!userDaoJdbc.existsById(user.getId())) {
            throw new RuntimeException("User not found with id: " + user.getId());
        }
        return userDaoJdbc.update(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userDaoJdbc.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userDaoJdbc.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean userExists(Long id) {
        return userDaoJdbc.existsById(id);
    }
}
