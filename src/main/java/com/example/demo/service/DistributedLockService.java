package com.example.demo.service;

import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Service
public class DistributedLockService {

    private final RedissonClient redissonClient;

    public DistributedLockService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public <T> T executeWithLock(String key, Callable<T> callable) {
        var lock = redissonClient.getLock(key);
        try {
            if (lock.tryLock(3, 10, TimeUnit.SECONDS)) {
                return callable.call();
            } else {
                throw new RuntimeException("Could not acquire lock for key: " + key);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
