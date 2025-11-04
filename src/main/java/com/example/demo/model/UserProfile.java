package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_profiles")
public class UserProfile {

    @Id
    private String id;

    private Long userId; // 关联 SQL User 的 ID
    private String bio;
    private String[] skills;

    public UserProfile() {}

    public UserProfile(Long userId, String bio, String[] skills) {
        this.userId = userId;
        this.bio = bio;
        this.skills = skills;
    }

    public String getId() { return id; }
    public Long getUserId() { return userId; }
    public String getBio() { return bio; }
    public String[] getSkills() { return skills; }

    public void setId(String id) { this.id = id; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setBio(String bio) { this.bio = bio; }
    public void setSkills(String[] skills) { this.skills = skills; }
}
