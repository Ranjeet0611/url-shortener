package com.url_shortener.service;

import com.url_shortener.model.UrlShortenRequest;

public interface UrlShortenerService {
    String getShortUrl(UrlShortenRequest urlShortenRequest);
}
