package com.url_shortener.controller;

import com.url_shortener.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class RedirectController {
    @Autowired
    private UrlShortenerService urlShortenerService;

    @GetMapping("/{alias}")
    public RedirectView redirect(@PathVariable String alias) {
        String longUrl = urlShortenerService.getLongUrl(alias);
        return new RedirectView(longUrl);
    }
}
