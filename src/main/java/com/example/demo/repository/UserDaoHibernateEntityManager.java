package com.example.demo.repository;

import com.example.demo.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Hibernate Implementation - Using EntityManager directly
 *
 * This approach uses JPA's EntityManager which is the standard Java Persistence API.
 * Hibernate is the implementation under the hood.
 * You write JPQL (Java Persistence Query Language) instead of SQL.
 *
 * Pros:
 * - Object-oriented queries (JPQL)
 * - Database-independent
 * - Less boilerplate than JDBC
 * - Automatic dirty checking and change detection
 * - First and second level caching
 *
 * Cons:
 * - Need to understand JPA lifecycle
 * - More abstraction can hide performance issues
 * - Learning curve for JPQL
 */
@Repository
@Transactional
public class UserDaoHibernateEntityManager {

    @PersistenceContext
    private EntityManager entityManager;

    public User save(User user) {
        if (user.getId() == null) {
            // New entity - persist it
            entityManager.persist(user);
            return user;
        } else {
            // Existing entity - merge it
            return entityManager.merge(user);
        }
    }

    public Optional<User> findById(Long id) {
        User user = entityManager.find(User.class, id);
        return Optional.ofNullable(user);
    }

    public List<User> findAll() {
        // JPQL query - notice we query the entity name, not table name
        TypedQuery<User> query = entityManager.createQuery(
            "SELECT u FROM User u", User.class
        );
        return query.getResultList();
    }

    public User update(User user) {
        // merge() updates the entity if it exists
        return entityManager.merge(user);
    }

    public void deleteById(Long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    public boolean existsById(Long id) {
        // JPQL query to check existence
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(u) FROM User u WHERE u.id = :id", Long.class
        );
        query.setParameter("id", id);
        return query.getSingleResult() > 0;
    }

    // Example of a custom query using JPQL
    public List<User> findByFirstName(String firstName) {
        TypedQuery<User> query = entityManager.createQuery(
            "SELECT u FROM User u WHERE u.firstName = :firstName", User.class
        );
        query.setParameter("firstName", firstName);
        return query.getResultList();
    }

    // Example of a named query (you can also define this in User entity with @NamedQuery)
    public List<User> findByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery(
            "SELECT u FROM User u WHERE u.email = :email", User.class
        );
        query.setParameter("email", email);
        return query.getResultList();
    }
}
