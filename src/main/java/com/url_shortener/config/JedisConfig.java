package com.url_shortener.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.UnifiedJedis;

@Configuration
public class JedisConfig {
    @Value("${spring.data.redis.host}")
    private String REDIS_HOST;

    @Value("${spring.data.redis.port}")
    private Integer REDIS_PORT;

    @Bean
    public JedisPool jedisPool() {
        return new JedisPool(REDIS_HOST, REDIS_PORT);
    }

    @Bean
    public UnifiedJedis unifiedJedis() {
        return new UnifiedJedis("redis://" + REDIS_HOST + ":" + REDIS_PORT);
    }
}
