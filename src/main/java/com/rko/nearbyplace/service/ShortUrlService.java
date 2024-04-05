package com.rko.nearbyplace.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
public class ShortUrlService {
    private final Cache<String, String> cache = CacheBuilder.newBuilder()
        .maximumSize(10000)
        .expireAfterWrite(24, TimeUnit.HOURS)
        .build();
    private final String BASE_URL = "http://localhost:8080/";

    public String shortenUrl(String originalUrl) {
        String shortKey = generateRandomKey(8);  // 8자리 랜덤 키 생성
        while (cache.getIfPresent(shortKey) != null) {
            shortKey = generateRandomKey(8);  // 중복되는 키가 있을 경우 재생성
        }
        cache.put(shortKey, originalUrl);
        return BASE_URL + shortKey;
    }

    public String getOriginalUrl(String shortKey) {
        return cache.getIfPresent(shortKey);
    }

    private String generateRandomKey(int length) {
        return ThreadLocalRandom.current().ints(48, 122)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
