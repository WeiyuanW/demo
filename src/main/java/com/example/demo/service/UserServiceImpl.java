package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    //  @Qualifier("jdbc") => "jdbc" can switch to "hibernate" or "jpa" for diff implementations
    public UserServiceImpl(@Qualifier("jdbc") UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getUserById(Long id) { return userDao.getUserById(id); }

    @Override
    public User saveUser(User user) { return userDao.saveUser(user); }

    @Override
    public void deleteUser(Long id) { userDao.deleteUser(id); }
}
