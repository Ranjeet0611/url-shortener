package com.url_shortener.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("url_document")
@Getter
@Setter
public class UrlDocument {
    @Id
    private String id;
    private String longUrl;
    private String shortUrl;
    private Date createdAt;
    private Date updatedAt;
    private Date expirationDate;
    private boolean isExpired;
}
