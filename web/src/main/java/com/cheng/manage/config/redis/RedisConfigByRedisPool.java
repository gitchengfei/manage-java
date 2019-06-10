package com.cheng.manage.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

/**
 * @Description :
 * @Author : cheng fei
 * @Date : 2019/3/25 21:41
 */
@Configuration
public class RedisConfigByRedisPool {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Bean
    public JedisPool jedisPool(){
        JedisPool jedisPool = new JedisPool(redisHost, redisPort);
        return jedisPool;
    }
}
