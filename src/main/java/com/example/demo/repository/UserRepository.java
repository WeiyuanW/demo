package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Implementation - Repository Interface
 *
 * This is the highest level of abstraction. You only define an interface,
 * and Spring Data JPA automatically provides the implementation at runtime.
 *
 * JpaRepository extends PagingAndSortingRepository which extends CrudRepository
 * and provides all basic CRUD operations out of the box.
 *
 * Pros:
 * - Minimal boilerplate code
 * - No implementation needed for standard operations
 * - Automatic query generation from method names
 * - Built-in pagination and sorting
 * - Type-safe queries
 * - Best for rapid development
 *
 * Cons:
 * - Less control over query execution
 * - Complex queries might be harder to write
 * - More "magic" - can be harder to debug
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data JPA automatically implements these methods based on naming convention!

    // Finds users by first name
    // Generated query: SELECT * FROM users WHERE first_name = ?
    List<User> findByFirstName(String firstName);

    // Finds users by last name
    List<User> findByLastName(String lastName);

    // Finds a user by email
    Optional<User> findByEmail(String email);

    // Finds users whose first name contains the given string (case-insensitive)
    List<User> findByFirstNameContainingIgnoreCase(String firstName);

    // Finds users by first name AND last name
    List<User> findByFirstNameAndLastName(String firstName, String lastName);

    // Custom JPQL query using @Query annotation
    @Query("SELECT u FROM User u WHERE u.email LIKE %:domain%")
    List<User> findByEmailDomain(@Param("domain") String domain);

    // Native SQL query (if you need database-specific features)
    @Query(value = "SELECT * FROM users WHERE first_name = ?1", nativeQuery = true)
    List<User> findByFirstNameNative(String firstName);

    // Check if a user exists by email
    boolean existsByEmail(String email);

    // Delete users by first name
    void deleteByFirstName(String firstName);

    // Count users by last name
    long countByLastName(String lastName);
}
