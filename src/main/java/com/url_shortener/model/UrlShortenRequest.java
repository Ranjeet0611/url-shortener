package com.url_shortener.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UrlShortenRequest {
    private String longUrl;
    private String customAlias;
    private Date expirationDate;
}
