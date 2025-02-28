package com.url_shortener.service.impl;

import com.url_shortener.model.UrlShortenRequest;
import com.url_shortener.service.UrlShortenerStrategy;
import lombok.Getter;
import lombok.Setter;

@Setter
public class UrlShortenerStrategyImpl {
    private UrlShortenerStrategy urlShortenerStrategy;

    public String generateShortUrl(UrlShortenRequest urlShortenRequest) {
        return this.urlShortenerStrategy.generateShortUrl(urlShortenRequest);
    }

}
