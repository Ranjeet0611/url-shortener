package com.url_shortener.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Date;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse<T> {
    private final String message;
    private final T data;
    private final Long timestamp;

    public ErrorResponse(ErrorResponseBuilder<T> errorResponseBuilder) {
        this.message = errorResponseBuilder.message;
        this.data = errorResponseBuilder.data;
        this.timestamp = errorResponseBuilder.timestamp;
    }

    public static class ErrorResponseBuilder<T> {
        private String message;
        private T data;
        private Long timestamp;

        public ErrorResponseBuilder<T> setMessage(String message) {
            this.message = message;
            return this;
        }

        public ErrorResponseBuilder<T> setData(T data) {
            this.data = data;
            return this;
        }

        private void setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
        }

        public ErrorResponse<T> build() {
            setTimestamp(new Date().getTime());
            return new ErrorResponse<>(this);
        }
    }
}
