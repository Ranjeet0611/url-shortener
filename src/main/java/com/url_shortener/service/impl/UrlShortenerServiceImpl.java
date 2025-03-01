package com.url_shortener.service.impl;

import com.url_shortener.model.UrlShortenRequest;
import com.url_shortener.repository.UrlShortenerRepository;
import com.url_shortener.service.CacheService;
import com.url_shortener.service.UrlShortenerService;
import com.url_shortener.service.UrlShortenerStrategy;
import com.url_shortener.util.UrlShortenerUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Slf4j
@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

    @Autowired
    @Qualifier("customAliasStrategy")
    private UrlShortenerStrategy customAliasStrategy;

    @Autowired
    @Qualifier("basicUrlShortenerStrategy")
    private UrlShortenerStrategy basicUrlShortenerStrategy;

    @Value("${redis.short.long.url.mapping}")
    private String SHORT_LONG_URL_MAPPING;

    @Autowired
    private UrlShortenerUtil urlShortenerUtil;

    @Autowired
    private UrlShortenerRepository urlShortenerRepository;

    @Autowired
    private CacheService cacheService;


    @Override
    public String createShortUrl(UrlShortenRequest urlShortenRequest) {
        try {
            log.info("Start createShortUrl :{}", urlShortenRequest);
            UrlShortenerStrategyImpl urlShortenerStrategy = new UrlShortenerStrategyImpl();
            if (StringUtils.isNoneEmpty(urlShortenRequest.getCustomAlias())) {
                urlShortenerStrategy.setUrlShortenerStrategy(customAliasStrategy);
                return urlShortenerStrategy.generateShortUrl(urlShortenRequest);
            }
            urlShortenerStrategy.setUrlShortenerStrategy(basicUrlShortenerStrategy);
            return urlShortenerStrategy.generateShortUrl(urlShortenRequest);
        } catch (Exception e) {
            log.error("Exception occurred while createShortUrl :{}", e.getMessage());
            throw e;
        }

    }

    @Override
    public String getLongUrl(String alias) {
        try {
            log.info("Start getShortUrl :{}", alias);
            String shortUrl = urlShortenerUtil.getShortUrl(alias);
            String longUrl = cacheService.hGet(SHORT_LONG_URL_MAPPING, shortUrl);
            if (StringUtils.isEmpty(longUrl)) {
                return urlShortenerRepository.getLongUrl(shortUrl);
            }
            return longUrl;
        } catch (Exception e) {
            log.error("Exception occurred while getLongUrl :{}", e.getMessage(), e);
            throw e;
        }
    }
}
