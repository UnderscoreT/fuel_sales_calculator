package com.blestcodestudios.fuelsalesapp.util;



import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

public class RateLimiter {
    // allow max 5 calls per minute per IP
    private static final Cache<String, Integer> counts = CacheBuilder.newBuilder()
        .expireAfterWrite(1, TimeUnit.MINUTES)
        .build();

    public static synchronized boolean allow(String ip) {
        Integer c = counts.getIfPresent(ip);
        if (c == null || c < 5) {
            counts.put(ip, (c == null ? 1 : c + 1));
            return true;
        }
        return false;
    }
}
