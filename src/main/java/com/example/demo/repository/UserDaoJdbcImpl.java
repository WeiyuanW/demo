package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@org.springframework.beans.factory.annotation.Qualifier("jdbc")
public class UserDaoJdbcImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> rowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(rs.getLong("id"), rs.getString("first_name"));
        }
    };

    @Override
    public User getUserById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", rowMapper, id);
    }

    @Override
    public User saveUser(User user) {
        jdbcTemplate.update("INSERT INTO users(first_name) VALUES(?)", user.getFirstName());
        // 这里简化，没返回生成的ID
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        jdbcTemplate.update("DELETE FROM users WHERE id = ?", id);
    }
}
