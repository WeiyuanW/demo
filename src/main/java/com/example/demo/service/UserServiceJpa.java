package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for Spring Data JPA implementation
 *
 * This is the simplest service implementation as Spring Data JPA's
 * JpaRepository already provides all the basic CRUD operations.
 * The service layer here is very thin, but it's still important to have
 * for future business logic and to keep the controller decoupled from
 * the repository layer.
 */
@Service("userServiceJpa")
@Transactional
public class UserServiceJpa implements UserService {

    private final UserRepository userRepository;

    public UserServiceJpa(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        // JpaRepository.save() handles both insert and update
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) {
        // findById is provided by JpaRepository
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        // findAll is provided by JpaRepository
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new RuntimeException("User not found with id: " + user.getId());
        }
        // save() handles both create and update
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        // deleteById is provided by JpaRepository
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean userExists(Long id) {
        // existsById is provided by JpaRepository
        return userRepository.existsById(id);
    }
}
