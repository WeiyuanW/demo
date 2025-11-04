package com.example.demo.repository;

import com.example.demo.model.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends MongoRepository<UserProfile, String> {
    UserProfile findByUserId(Long userId);
}
