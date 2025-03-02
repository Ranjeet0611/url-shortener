package com.url_shortener.scheduler;

import com.url_shortener.model.UrlDocument;
import com.url_shortener.repository.UrlShortenerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class UrlShortenerScheduler {
    @Autowired
    private UrlShortenerRepository urlShortenerRepository;

    @Scheduled(cron = "${cron.expression.to.expire.url}")
    public void checkForUrlToExpire() {
        try {
            log.info("Start checking url to mark expire");
            List<UrlDocument> allUrlForExpiration = urlShortenerRepository.getAllUrlForExpiration();
            for (UrlDocument urlDocument : allUrlForExpiration) {
                urlDocument.setExpired(true);
            }
            urlShortenerRepository.saveAll(allUrlForExpiration);
            log.info("End checking url to mark expire");
        } catch (Exception e) {
            log.error("Exception occurred while checkForUrlToExpire :{}", e.getMessage(), e);
        }
    }
}
