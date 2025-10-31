package com.example.demo.repository;

import com.example.demo.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory; // Import native Hibernate SessionFactory
import org.springframework.beans.factory.annotation.Autowired; // Import Spring's Autowired
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.TypedQuery; // This can stay, as session.createQuery returns it

import java.util.List;
import java.util.Optional;

/**
 * Hibernate Implementation - Using native Session directly
 *
 * This approach uses Hibernate's native Session API.
 * It's more specific than JPA and provides access to all of Hibernate's features.
 *
 * Pros:
 * - Full access to all Hibernate-specific features.
 * - Still provides caching, dirty checking, etc.
 *
 * Cons:
 * - Ties your code directly to Hibernate (vendor lock-in).
 * - Need to manage Sessions (though Spring helps with @Transactional).
 */
@Repository
@Transactional
public class UserDaoHibernateSession {

    // 1. Inject the SessionFactory
    @Autowired
    private SessionFactory sessionFactory;

    // Helper method to get the current session managed by Spring's transaction
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public User save(User user) {
        Session session = getCurrentSession();
        if (user.getId() == null) {
            // 2. Use session.persist() (JPA-aligned method)
            // (The classic Hibernate-only method is session.save())
            session.persist(user);
            return user;
        } else {
            // 3. Use session.merge() (JPA-aligned method)
            // (The classic Hibernate-only method is session.saveOrUpdate())
            return session.merge(user);
        }
    }

    public Optional<User> findById(Long id) {
        // 4. Use session.get(), the native equivalent of find()
        User user = getCurrentSession().get(User.class, id);
        return Optional.ofNullable(user);
    }

    public List<User> findAll() {
        // 5. Call createQuery on the session
        TypedQuery<User> query = getCurrentSession().createQuery(
                "SELECT u FROM User u", User.class
        );
        return query.getResultList();
    }

    public User update(User user) {
        // merge() is identical in this context
        return getCurrentSession().merge(user);
    }

    public void deleteById(Long id) {
        Session session = getCurrentSession();
        // Use session.get() to retrieve the object
        User user = session.get(User.class, id);
        if (user != null) {
            // 6. Use session.remove() (JPA-aligned)
            // (The classic Hibernate-only method is session.delete())
            session.remove(user);
        }
    }

    public boolean existsById(Long id) {
        // The query is identical, just called on the session
        TypedQuery<Long> query = getCurrentSession().createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.id = :id", Long.class
        );
        query.setParameter("id", id);
        return query.getSingleResult() > 0;
    }

    // Custom queries are also identical, just called on the session
    public List<User> findByFirstName(String firstName) {
        TypedQuery<User> query = getCurrentSession().createQuery(
                "SELECT u FROM User u WHERE u.firstName = :firstName", User.class
        );
        query.setParameter("firstName", firstName);
        return query.getResultList();
    }

    public List<User> findByEmail(String email) {
        TypedQuery<User> query = getCurrentSession().createQuery(
                "SELECT u FROM User u WHERE u.email = :email", User.class
        );
        query.setParameter("email", email);
        return query.getResultList();
    }
}
