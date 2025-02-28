package com.url_shortener.service;

public interface BloomFilterService {
    boolean checkIfAliasExists(String customAlias);
}
