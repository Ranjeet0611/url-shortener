package com.url_shortener.repository;

import com.url_shortener.model.UrlDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlShortenerRepository extends MongoRepository<UrlDocument, String>, CustomUrlShortenerRepository {
}
