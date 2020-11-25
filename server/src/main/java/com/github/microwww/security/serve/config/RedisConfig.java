package com.github.microwww.security.serve.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
        template.setValueSerializer(serializer);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 对redis字符串类型数据操作
     *
     * @param factory
     * @return
     */
    @Bean
    public ValueOperations<String, Serializable> valueOperations(RedisConnectionFactory factory) {
        RedisTemplate<String, Serializable> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        JdkSerializationRedisSerializer serializer = new JdkSerializationRedisSerializer(this.getClass().getClassLoader());
        template.setValueSerializer(serializer);
        template.afterPropertiesSet();
        return template.opsForValue();
    }

}