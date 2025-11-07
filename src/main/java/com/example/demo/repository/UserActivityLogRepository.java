package com.example.demo.repository;

import com.example.demo.model.UserActivityLog;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActivityLogRepository extends CassandraRepository<UserActivityLog, String> {
}
