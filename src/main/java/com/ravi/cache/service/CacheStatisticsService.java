package com.ravi.cache.service;

import com.ravi.dto.CacheStatistics;
import net.sf.ehcache.Statistics;

public interface CacheStatisticsService {

    Statistics getCacheStatistics(String cacheName);
}
