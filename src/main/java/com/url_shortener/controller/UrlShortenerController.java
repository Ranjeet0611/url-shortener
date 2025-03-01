package com.url_shortener.controller;

import com.url_shortener.model.Response;
import com.url_shortener.model.UrlShortenRequest;
import com.url_shortener.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/v1")
public class UrlShortenerController {
    @Autowired
    private UrlShortenerService urlShortenerService;

    @PostMapping("/urls")
    public ResponseEntity<Response<String>> getShortUrl(@RequestBody UrlShortenRequest urlShortenRequest) {
        String shortUrl = urlShortenerService.createShortUrl(urlShortenRequest);
        Response<String> shortMessageCreatedSuccessfully = new Response.ResponseBuilder<String>().setData(shortUrl).setMessage("Short url created Successfully").build();
        return new ResponseEntity<>(shortMessageCreatedSuccessfully, HttpStatus.OK);
    }
}
