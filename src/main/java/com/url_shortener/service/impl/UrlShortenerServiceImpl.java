package com.url_shortener.service.impl;

import com.url_shortener.model.UrlShortenRequest;
import com.url_shortener.service.GlobalCounterService;
import com.url_shortener.service.UrlShortenerService;
import com.url_shortener.service.UrlShortenerStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

    @Autowired
    @Qualifier("customAliasStrategy")
    private UrlShortenerStrategy customAliasStrategy;
    @Autowired
    @Qualifier("basicUrlShortenerStrategy")
    private UrlShortenerStrategy basicUrlShortenerStrategy;

    @Override
    public String getShortUrl(UrlShortenRequest urlShortenRequest) {
        try {
            log.info("Start getShortUrl :{}", urlShortenRequest);
            UrlShortenerStrategyImpl urlShortenerStrategy = new UrlShortenerStrategyImpl();
            if (StringUtils.isNoneEmpty(urlShortenRequest.getCustomAlias())) {
                urlShortenerStrategy.setUrlShortenerStrategy(customAliasStrategy);
                return urlShortenerStrategy.generateShortUrl(urlShortenRequest);
            }
            urlShortenerStrategy.setUrlShortenerStrategy(basicUrlShortenerStrategy);
            return urlShortenerStrategy.generateShortUrl(urlShortenRequest);
        } catch (Exception e) {
            log.error("Exception occurred while getShortUrl :{}", e.getMessage());
            throw e;
        }

    }
}
