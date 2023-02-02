package com.losmilos.flightadvisor.exception.handler;

import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.lang.Nullable;

public class CustomCacheErrorHandler implements CacheErrorHandler {

  @Override
  public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {

  }

  @Override
  public void handleCachePutError(RuntimeException exception, Cache cache, Object key, @Nullable Object value) {

  }

  @Override
  public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {

  }

  @Override
  public void handleCacheClearError(RuntimeException exception, Cache cache) {

  }

}
