package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@org.springframework.beans.factory.annotation.Qualifier("jpa")
public interface UserRepository extends JpaRepository<User, Long>, UserDao {
    // JpaRepository 已经提供 get/save/delete 方法
}
