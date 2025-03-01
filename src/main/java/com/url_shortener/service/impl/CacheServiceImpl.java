package com.url_shortener.service.impl;

import com.url_shortener.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
@Slf4j
public class CacheServiceImpl extends CacheService {
    @Override
    public long hSet(String cacheKey, String key, String value) {
        try {
            log.info("Start hSet :{} :{} :{}", cacheKey, key, value);
            Jedis resource = getResource();
            return resource.hset(cacheKey, key, value);
        } catch (Exception e) {
            log.error("Exception occurred while hSet :{}", e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public String hGet(String cacheKey, String key) {
        try {
            log.info("Start hGet :{} :{}", cacheKey, key);
            Jedis resource = getResource();
            return resource.hget(cacheKey, key);
        } catch (Exception e) {
            log.error("Exception occurred while hGet :{}", e.getMessage(), e);
        }
        return null;
    }
}
