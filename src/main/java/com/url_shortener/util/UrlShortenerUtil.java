package com.url_shortener.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UrlShortenerUtil {

    @Value("${spring.application.host}")
    private String SERVICE_HOST;
    @Value("${spring.application.port}")
    private int SERVICE_PORT;

    public String getShortUrl(String alias) {
        return "http://" + SERVICE_HOST + ":" + SERVICE_PORT + "/" + alias;

    }
}
