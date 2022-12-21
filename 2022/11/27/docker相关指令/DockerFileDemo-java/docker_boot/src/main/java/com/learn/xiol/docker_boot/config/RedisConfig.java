package com.learn.xiol.docker_boot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

/**
 * <p>redis配置类</p>
 * @author luowei
 * @date 2022-12-18 21:30
 * @desc redis配置类
 */
@Configuration
@Slf4j
public class RedisConfig {

    /**
     * redis序列化的工具配置类，下面这个请一定要开启配置
     *  127.0.0.1:6379> keys *
     *      1) "ord:102"  序列化过
     *      2) "\xac\xed\x00\x05t\x00\aord:102"   野生，没有序列化过
     * @param lettuceConnectionFactory
     * @return redis模板
     */
    @Bean
    public RedisTemplate<String, Serializable> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory){

        // 1. 创建一个redis模板实例
        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
        // 2. 设置连接工厂
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        // 3、设置key序列化string
        redisTemplate.setKeySerializer( new StringRedisSerializer());
        // 4、设置value的序列化方式json
        redisTemplate.setValueSerializer( new GenericJackson2JsonRedisSerializer());

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
}
