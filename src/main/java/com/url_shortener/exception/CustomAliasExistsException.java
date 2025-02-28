package com.url_shortener.exception;

public class CustomAliasExistsException extends RuntimeException {
    String message;
    public CustomAliasExistsException(String message) {
        super(message);
        this.message = message;
    }
}
