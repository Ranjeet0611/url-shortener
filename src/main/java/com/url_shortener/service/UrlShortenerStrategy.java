package com.url_shortener.service;

import com.url_shortener.model.UrlShortenRequest;

public interface UrlShortenerStrategy {
    String generateShortUrl(UrlShortenRequest urlShortenRequest);
}
