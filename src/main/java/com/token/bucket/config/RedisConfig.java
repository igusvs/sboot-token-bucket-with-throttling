package com.token.bucket.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;


    @Bean
    public JedisPool jedisPool(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        return new JedisPool(poolConfig, redisHost, redisPort, Protocol.DEFAULT_TIMEOUT);
    }
}
