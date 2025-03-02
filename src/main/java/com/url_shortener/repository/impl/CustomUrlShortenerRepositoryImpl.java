package com.url_shortener.repository.impl;

import com.url_shortener.exception.ShortUrlNotFoundException;
import com.url_shortener.model.UrlDocument;
import com.url_shortener.model.UrlShortenRequest;
import com.url_shortener.repository.CustomUrlShortenerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.url_shortener.constant.UrlShortenerConstant.EXPIRATION_DATE;
import static com.url_shortener.constant.UrlShortenerConstant.IS_EXPIRED;

@Slf4j
public class CustomUrlShortenerRepositoryImpl implements CustomUrlShortenerRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(UrlShortenRequest urlShortenRequest, String shortUrl) {
        log.info("Start save :{}", shortUrl);
        UrlDocument urlDocument = new UrlDocument();
        urlDocument.setId(UUID.randomUUID().toString());
        urlDocument.setLongUrl(urlShortenRequest.getLongUrl());
        urlDocument.setShortUrl(shortUrl);
        urlDocument.setCreatedAt(new Date());
        urlDocument.setUpdatedAt(new Date());
        urlDocument.setExpirationDate(urlShortenRequest.getExpirationDate());
        log.info("End save :{}", shortUrl);
        mongoTemplate.save(urlDocument);
    }

    @Override
    public List<UrlDocument> getAllUrlForExpiration() {
        try {
            log.info("Start getAllUrlForExpiration");
            Query query = Query.query(Criteria.where(EXPIRATION_DATE).lte(new Date()));
            List<UrlDocument> urlDocuments = mongoTemplate.find(query, UrlDocument.class);
            log.info("url documents :{}", urlDocuments);
            return urlDocuments;
        } catch (Exception e) {
            log.error("Exception occurred while getAllUrlForExpiration :{}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public String getLongUrl(String shortUrl) {
        try {
            log.info("Start getLongUrl :{}", shortUrl);
            Query query = Query.query(Criteria.where(shortUrl).is(shortUrl).and(IS_EXPIRED).is(false));
            List<UrlDocument> urlDocuments = mongoTemplate.find(query, UrlDocument.class);
            if (CollectionUtils.isEmpty(urlDocuments)) {
                throw new ShortUrlNotFoundException("Short url not found or either expired");
            }
            return urlDocuments.get(0).getLongUrl();
        } catch (Exception e) {
            log.error("Exception occurred while getLongUrl :{}", e.getMessage(), e);
            throw e;
        }
    }
}
