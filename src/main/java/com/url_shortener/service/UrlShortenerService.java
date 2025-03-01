package com.url_shortener.service;

import com.url_shortener.model.UrlShortenRequest;

public interface UrlShortenerService {
    String createShortUrl(UrlShortenRequest urlShortenRequest);

    String getLongUrl(String alias);
}
