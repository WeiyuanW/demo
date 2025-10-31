package com.example.demo.repository;

import com.example.demo.model.User;
import java.util.List;

public interface UserDao {
    User getUserById(Long id);
    User saveUser(User user);
    void deleteUser(Long id);
}
