package com.losmilos.flightadvisor.config;

import com.losmilos.flightadvisor.exception.handler.CustomCacheErrorHandler;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.resource.ClientResources;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

  @Value("${spring.redis.host}")
  private String redisHost;

  @Value("${spring.redis.port}")
  private int redisPort;

  @Bean
  public RedisStandaloneConfiguration redisStandaloneConfiguration() {
    return new RedisStandaloneConfiguration(redisHost, redisPort);
  }

  @Bean
  public ClientOptions clientOptions() {
    return ClientOptions.builder()
        .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
        .autoReconnect(true)
        .build();
  }

  @Bean
  LettucePoolingClientConfiguration lettucePoolConfig(ClientOptions options, ClientResources dcr) {
    final var lettucePoolingClientConfigurationBuilder =
        LettucePoolingClientConfiguration.builder()
            .poolConfig(new GenericObjectPoolConfig())
            .clientOptions(options)
            .clientResources(dcr);
    return lettucePoolingClientConfigurationBuilder.build();
  }

  @Bean
  public RedisConnectionFactory connectionFactory(
      RedisStandaloneConfiguration redisStandaloneConfiguration,
      LettucePoolingClientConfiguration lettucePoolConfig) {
    return new LettuceConnectionFactory(redisStandaloneConfiguration, lettucePoolConfig);
  }

  public CacheErrorHandler errorHandler() {
    return new CustomCacheErrorHandler();
  }

}
