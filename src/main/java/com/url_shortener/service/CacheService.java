package com.url_shortener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public abstract class CacheService {
    @Autowired
    private JedisPool jedisPool;

    public Jedis getResource() {
        return jedisPool.getResource();
    }

    public abstract long hSet(String cacheKey, String key, String value);

    public abstract String hGet(String cacheKey, String key);

}
