package com.ravi.cache.service.impl;

import com.ravi.cache.service.CacheStatisticsService;
import com.ravi.dto.CacheStatistics;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import net.sf.ehcache.Statistics;

@Component
public class CacheStatisticsServiceImpl implements CacheStatisticsService {
    private static final Logger LOG = LoggerFactory.getLogger(CacheStatisticsServiceImpl.class);

    @Autowired
    private CacheManager cacheManager;

    public Statistics getCacheStatistics(String cacheName){
        //CacheStatistics cacheStatistics = null;
        Cache teacherCache = cacheManager.getCache(cacheName);
        Statistics statistics = teacherCache.getStatistics();
       /* cacheStatistics = new CacheStatistics();
        cacheStatistics.setCacheHits(statistics.getCacheHits());
        cacheStatistics.setAverageGetTime(statistics.getAverageGetTime());


        Cache teacherCache = cacheManager.getCache(cacheName);
        String cacheStatistics = teacherCache.getStatistics().toString();
        return cacheStatistics;*/

        return statistics;
    }
}
