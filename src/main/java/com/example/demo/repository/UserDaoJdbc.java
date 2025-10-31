package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * JDBC Implementation - Direct SQL queries using JdbcTemplate
 *
 * This is the most manual approach where you write raw SQL queries
 * and manually map ResultSet to your domain objects.
 *
 * Pros:
 * - Full control over SQL
 * - Best performance for complex queries
 * - Easy to optimize specific queries
 *
 * Cons:
 * - More boilerplate code
 * - Manual result set mapping
 * - Database-specific SQL
 */
@Repository
public class UserDaoJdbc {

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    public UserDaoJdbc(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

    // RowMapper to convert ResultSet to User object
    private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        return user;
    };

    /**
     *
     *  JDBC
     *    SpringBoot application        (JDBC Driver  + Credentials) Connection         PostgreSQL
     *
     *
     *
     * public class JDBCCOnfig {
     *
     *  @Value("spring.datasource.url)
     *  private String url;
     *
     *
     * }
     *
     * public class ConnectionPool {
     *
     *             Connection connection = DriverManager.getConnection(
     *                             JdbcConfig.getURL();
     *                             JdbcConfig.getUserName();
     *                             JdbcConfig.getPassword();
     *
     *         );
     * }
     *
     * Spring sql = "SELECT * FROM table1 WHERE id=";
     * String userId = user.getUserId();
     * String statement = sql + userId;
     *
     * SELECT * FROM table1 WHERE password=123 OR 1==1
     *
     * List<User>
     *
     */
    public User save(User user) {
//
//      String sql = "SELECT * FROM table1 WHERE id=";
//      String userId = user.getUserId();
//      String statement = sql + userId;


        String sql = "INSERT INTO users (first_name, last_name, email) VALUES (?, ?, ?)";
        // ConnectionPool.getConnection().query();
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            return ps;
        }, keyHolder);

        user.setId(keyHolder.getKey().longValue());
        return user;
    }

    /**
     * Batch insert multiple users using JDBC batch processing
     *
     * This method demonstrates proper transaction management with:
     * - Manual connection handling
     * - Batch processing for better performance
     * - Try-catch-finally for error handling
     * - Rollback on failure
     */
    public void batchSave(List<User> users) {
//
//      String sql = "SELECT * FROM table1 WHERE id=";
//      String userId = user.getUserId();
//      String statement = sql + userId;

        String sql = "INSERT INTO users (first_name, last_name, email) VALUES (?, ?, ?)";
        // ConnectionPool.getConnection().query();

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Get connection from DataSource
            connection = dataSource.getConnection();

            // Disable auto-commit for transaction management
            connection.setAutoCommit(false);

            // Prepare the statement
            preparedStatement = connection.prepareStatement(sql);

            // Add each user to the batch
            for (User user : users) {
                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.setString(2, user.getLastName());
                preparedStatement.setString(3, user.getEmail());
                /**
                 * batchId
                 *  - jobid
                 *  -
                 *
                 */
                preparedStatement.addBatch();
            }

            // Execute the batch
            preparedStatement.executeBatch();

            // Commit the transaction
            connection.commit();

        } catch (SQLException e) {
            // Rollback transaction on error
            if (connection != null) {
                try {
                    connection.rollback();
                    // retry (jobID)
                } catch (SQLException rollbackException) {
                    throw new RuntimeException("Error rolling back batch insert: " + rollbackException.getMessage(), rollbackException);
                }
            }
            throw new RuntimeException("Error performing batch insert: " + e.getMessage(), e);
        } finally {
            // Clean up resources
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    // Log but don't throw
                }
            }
            if (connection != null) {
                try {
                    connection.setAutoCommit(true); // Reset auto-commit
                    connection.close();
                } catch (SQLException e) {
                    // Log but don't throw
                }
            }
        }














    }

    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, id);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    public User update(User user) {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, email = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getId()
        );
        return user;
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM users WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
}
