package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserDaoHibernateEntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for Hibernate (EntityManager) implementation
 *
 * This service delegates to the Hibernate DAO which uses EntityManager.
 * The @Transactional annotation is particularly important here as
 * Hibernate requires an active transaction for most operations.
 */
@Service("userServiceHibernate")
@Transactional
public class UserServiceHibernate implements UserService {

    private final UserDaoHibernateEntityManager userDaoHibernate;

    public UserServiceHibernate(UserDaoHibernateEntityManager userDaoHibernate) {
        this.userDaoHibernate = userDaoHibernate;
    }

    @Override
    public User createUser(User user) {
        return userDaoHibernate.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) {
        return userDaoHibernate.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDaoHibernate.findAll();
    }

    @Override
    public User updateUser(User user) {
        if (!userDaoHibernate.existsById(user.getId())) {
            throw new RuntimeException("User not found with id: " + user.getId());
        }
        return userDaoHibernate.update(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userDaoHibernate.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userDaoHibernate.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean userExists(Long id) {
        return userDaoHibernate.existsById(id);
    }
}
