package com.example.demo.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("user_activity_log")
public class UserActivityLog {

    @PrimaryKey
    private String id;

    private Long userId;
    private String action;
    private long timestamp;

    public UserActivityLog() {}

    public UserActivityLog(String id, Long userId, String action, long timestamp) {
        this.id = id;
        this.userId = userId;
        this.action = action;
        this.timestamp = timestamp;
    }

    public String getId() { return id; }
    public Long getUserId() { return userId; }
    public String getAction() { return action; }
    public long getTimestamp() { return timestamp; }

    public void setId(String id) { this.id = id; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setAction(String action) { this.action = action; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
