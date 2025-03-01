package com.url_shortener.service.impl;

import com.url_shortener.model.UrlShortenRequest;
import com.url_shortener.repository.UrlShortenerRepository;
import com.url_shortener.service.CacheService;
import com.url_shortener.service.GlobalCounterService;
import com.url_shortener.service.UrlShortenerStrategy;
import com.url_shortener.util.UrlShortenerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component("basicUrlShortenerStrategy")
@Slf4j
public class BasicUrlShortenerStrategy implements UrlShortenerStrategy {

    @Autowired
    private GlobalCounterService globalCounterService;

    @Autowired
    private UrlShortenerUtil urlShortenerUtil;

    @Autowired
    private UrlShortenerRepository urlShortenerRepository;

    @Autowired
    private CacheService cacheService;

    @Value("${redis.short.long.url.mapping.key}")
    private String SHORT_LONG_URL_MAPPING;

    @Override
    public String generateShortUrl(UrlShortenRequest urlShortenRequest) {
        try {
            log.info("Start generateShortUrl :{}", urlShortenRequest);
            Long incrementValue = globalCounterService.incrementValue();
            byte[] longAsByteArray = getLongAsByteArray(incrementValue);
            String ecodedString = Base64.getEncoder().encodeToString(longAsByteArray);
            String shortUrl = urlShortenerUtil.getShortUrl(ecodedString);
            urlShortenerRepository.save(urlShortenRequest, shortUrl);
            cacheService.hSet(SHORT_LONG_URL_MAPPING,shortUrl,urlShortenRequest.getLongUrl());
            log.info("Short url created :{} ",shortUrl);
            return shortUrl;
        } catch (Exception e) {
            log.error("Exception occurred while generateShortUrl :{}", e.getMessage(), e);
            throw e;
        }
    }

    private byte[] getLongAsByteArray(Long incrementValue) {
        byte[] byteArray = new byte[8];
        for (int i = 0; i < 8; i++) {
            byteArray[7 - i] = (byte) (incrementValue >>> (i * 8));
        }
        return byteArray;
    }
}
