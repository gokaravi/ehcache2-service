package com.ravi.cache.service;

import com.ravi.dto.CacheStatistics;

public interface CacheStatisticsService {

    CacheStatistics getCacheStatistics(String cacheName);
}
