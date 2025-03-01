package com.url_shortener.service.impl;

import com.url_shortener.exception.CustomAliasExistsException;
import com.url_shortener.model.UrlShortenRequest;
import com.url_shortener.repository.UrlShortenerRepository;
import com.url_shortener.service.BloomFilterService;
import com.url_shortener.service.CacheService;
import com.url_shortener.service.UrlShortenerStrategy;
import com.url_shortener.util.UrlShortenerUtil;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component("customAliasStrategy")
@Slf4j
public class CustomAliasStrategy implements UrlShortenerStrategy {
    @Autowired
    private BloomFilterService bloomFilterService;

    @Autowired
    private UrlShortenerRepository urlShortenerRepository;

    @Autowired
    private UrlShortenerUtil urlShortenerUtil;

    @Autowired
    private CacheService cacheService;

    @Value("${redis.short.long.url.mapping.key}")
    private String SHORT_LONG_URL_MAPPING;

    @Override
    public String generateShortUrl(UrlShortenRequest urlShortenRequest) {
        try {
            log.info("Start generateShortUrl :{}", urlShortenRequest);
            if (StringUtils.isEmpty(urlShortenRequest.getCustomAlias())) {
                log.info("No custom alias present in request");
            }
            boolean isCustomAliasExists = bloomFilterService.checkIfAliasExists(urlShortenRequest.getCustomAlias());
            if (isCustomAliasExists) {
                throw new CustomAliasExistsException("Custom alias already exists " + urlShortenRequest.getCustomAlias());
            }
            String shortUrl = urlShortenerUtil.getShortUrl(urlShortenRequest.getCustomAlias());
            urlShortenerRepository.save(urlShortenRequest, shortUrl);
            cacheService.hSet(SHORT_LONG_URL_MAPPING,shortUrl,urlShortenRequest.getLongUrl());
            return shortUrl;

        } catch (Exception e) {
            log.error("Exception occurred while generateShortUrl :{}", e.getMessage(), e);
            throw e;
        }
    }
}
