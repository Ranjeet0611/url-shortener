package com.url_shortener.service.impl;

import com.url_shortener.service.BloomFilterService;
import io.micrometer.common.util.StringUtils;
import io.netty.util.internal.StringUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.UnifiedJedis;

import static com.url_shortener.constant.UrlShortenerConstant.BLOOM_FILTER_CREATED_VALUE;

@Service
@Slf4j
public class BloomFilterServiceImpl implements BloomFilterService {
    @Autowired
    private UnifiedJedis unifiedJedis;
    @Value("${redis.bloom.filter.key}")
    private String BLOOM_FILTER_KEY;
    @Value("${redis.bloom.init.key}")
    private String BLOOM_FILTER_CREATED;

    @PostConstruct
    public void init() {
        String isCreated = unifiedJedis.get(BLOOM_FILTER_CREATED);
        if (StringUtils.isEmpty(isCreated)) {
            unifiedJedis.bfReserve(BLOOM_FILTER_KEY, 0.01, 10000);
            unifiedJedis.set(BLOOM_FILTER_CREATED, BLOOM_FILTER_CREATED_VALUE);
        }
    }

    @Override
    public boolean checkIfAliasExists(String customAlias) {
        try {
            log.info("Start checkIfAliasExists :{}", customAlias);
            boolean isCustomAliasExists = unifiedJedis.bfExists(BLOOM_FILTER_KEY, customAlias);
            if (isCustomAliasExists) {
                return true;
            }
            unifiedJedis.bfAdd(BLOOM_FILTER_KEY, customAlias);
            return false;

        } catch (Exception e) {
            log.error("Exception occurred while checkIfAliasExists :{}", e.getMessage(), e);
            throw e;
        }
    }
}
