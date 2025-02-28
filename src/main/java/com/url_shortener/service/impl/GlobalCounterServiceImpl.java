package com.url_shortener.service.impl;

import com.url_shortener.service.GlobalCounterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
@Slf4j
public class GlobalCounterServiceImpl implements GlobalCounterService {
    @Autowired
    private JedisPool jedisPool;
    @Value("redis.global.counter.key")
    private String GLOBAL_COUNTER_KEY;

    @Override
    public String getValue() {
        try {
            log.info("Start get global counter value");
            Jedis resource = jedisPool.getResource();
            return resource.get(GLOBAL_COUNTER_KEY);
        } catch (Exception e) {
            log.error("Exception occurred while getValue :{}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Long incrementValue() {
        try {
            log.info("Start increase global counter value");
            Jedis resource = jedisPool.getResource();
            return resource.incr(GLOBAL_COUNTER_KEY);
        } catch (Exception e) {
            log.error("Exception occurred while increment value in redis :{}", e.getMessage(), e);
            throw e;
        }
    }
}
