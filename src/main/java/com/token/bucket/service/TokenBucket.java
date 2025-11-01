package com.token.bucket.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class TokenBucket {

    private final RedisTemplate<String, String> redisTemplate;
    private final int bucketCapacity =  1; // Maximum tokens the bucket can hold
    private final double refillRate = 1; // Tokens refilled per second

    public TokenBucket(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Long incrementCounter(String key) {
        Long result = redisTemplate.opsForValue().increment(key);

        if(Objects.nonNull(result) && result == 1) {
           redisTemplate.expire(key, 2L, TimeUnit.MINUTES);
        }

        return result;
    }

    public Long incrementCounterBy(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta); // Increments by delta
    }

    public Long decrementCounter(String key) {
        return redisTemplate.opsForValue().decrement(key); // Decrements by 1
    }

    public Long decrementCounterBy(String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta); // Decrements by delta
    }

}
