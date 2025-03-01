package com.url_shortener.exception;

import lombok.Getter;

@Getter
public class ShortUrlNotFoundException extends RuntimeException {
    String message;

    public ShortUrlNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
