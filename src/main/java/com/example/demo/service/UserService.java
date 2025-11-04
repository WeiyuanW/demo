package com.example.demo.service;

import com.example.demo.model.User;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User createUser(User user);

    Optional<User> getUserById(Long id);

    List<User> getAllUsers();

    User updateUser(User user);

    void deleteUser(Long id);

    boolean userExists(Long id);

    User updateOptimistic(@Valid User user);

    User updatePessimistic(@Valid User user);
}
