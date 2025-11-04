package com.example.demo.service;

import com.example.demo.model.UserProfile;
import com.example.demo.repository.UserProfileRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    private final UserProfileRepository mongoRepo;

    public UserProfileService(UserProfileRepository mongoRepo) {
        this.mongoRepo = mongoRepo;
    }

    @Cacheable(value = "userProfileCache", key = "#userId")
    public UserProfile getProfileByUserId(Long userId) {
        return mongoRepo.findByUserId(userId);
    }
}
