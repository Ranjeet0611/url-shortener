package com.url_shortener.repository;

import com.url_shortener.model.UrlDocument;
import com.url_shortener.model.UrlShortenRequest;

import java.util.List;

public interface CustomUrlShortenerRepository {
    void save(UrlShortenRequest urlShortenRequest, String shortUrl);

    List<UrlDocument> getAllUrlForExpiration();

    String getLongUrl(String shortUrl);
}
