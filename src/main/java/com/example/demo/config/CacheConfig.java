package com.example.demo.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {
    // Redis 已在 application.properties 配置
    // @Cacheable 可直接用于 Service 层方法
}
