package com.url_shortener.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UrlShortenerUtil {

    @Value("${spring.application.host}")
    private String SERVICE_HOST;

    public String getShortUrl(String alias) {
        return "http://" + SERVICE_HOST + "/" + alias;

    }
}
