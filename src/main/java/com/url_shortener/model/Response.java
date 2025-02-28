package com.url_shortener.model;

import lombok.Getter;

import java.util.Date;

@Getter
public class Response<T> {
    private final T data;
    private final String message;
    private final long timestamp;

    public Response(ResponseBuilder<T> responseBuilder) {
        this.data = responseBuilder.data;
        this.message = responseBuilder.message;
        this.timestamp = responseBuilder.timestamp;
    }

    public static class ResponseBuilder<T> {
        private T data;
        private String message;
        private long timestamp;

        public ResponseBuilder<T> setData(T data) {
            this.data = data;
            return this;
        }

        public ResponseBuilder<T> setMessage(String message) {
            this.message = message;
            return this;
        }

        private void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public Response<T> build() {
            setTimestamp(new Date().getTime());
            return new Response<>(this);
        }
    }
}
