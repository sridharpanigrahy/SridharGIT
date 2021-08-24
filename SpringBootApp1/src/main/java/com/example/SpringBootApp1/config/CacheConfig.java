package com.example.SpringBootApp1.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig
{
    /* Cache Manager provided by spring internally when we are not using any other CacheProvider. */
    @Bean
    public CacheManager cacheManager()
    {
        return new org.springframework.cache.concurrent.ConcurrentMapCacheManager();
    }
}
