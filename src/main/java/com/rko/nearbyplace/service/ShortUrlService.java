package com.rko.nearbyplace.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ShortUrlService {
    private final ConcurrentHashMap<String, String> urlMap = new ConcurrentHashMap<>();
    private final String BASE_URL = "http://localhost:8080/";

    public String shortenUrl(String originalUrl) {
        String shortKey = generateRandomKey(8);  // 8자리 랜덤 키 생성
        while (urlMap.containsKey(shortKey)) {
            shortKey = generateRandomKey(8);  // 중복되는 키가 있을 경우 재생성
        }
        urlMap.put(shortKey, originalUrl);
        return BASE_URL + shortKey;
    }

    public String getOriginalUrl(String shortKey) {
        return urlMap.get(shortKey);
    }

    private String generateRandomKey(int length) {
        return ThreadLocalRandom.current().ints(48, 122)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
