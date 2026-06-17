package com.mcmanuel.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.DefaultTyping;
import tools.jackson.databind.JacksonModule;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import tools.jackson.databind.module.SimpleModule;

import java.time.Duration;

@Configuration
public class UserRedisConfiguration {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory){
        BasicPolymorphicTypeValidator validator = BasicPolymorphicTypeValidator.builder()
                .allowIfBaseType("com.mcmanuel")
                .allowIfBaseType("java.util")
                .allowIfBaseType("java.lang")
                .build();

        ObjectMapper mapper = JsonMapper.builder()
                .activateDefaultTyping(validator,DefaultTyping.NON_FINAL).build();

        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(6))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer( new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer( new GenericJacksonJsonRedisSerializer(mapper)))
                ;

         return RedisCacheManager.builder(factory).cacheDefaults(config).build();
    }


}
