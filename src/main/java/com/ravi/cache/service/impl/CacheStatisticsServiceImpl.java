package com.ravi.cache.service.impl;

import com.ravi.cache.service.CacheStatisticsService;
import com.ravi.dto.CacheStatistics;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CacheStatisticsServiceImpl extends BaseCacheStatisticsServiceImpl implements CacheStatisticsService{
    private static final Logger LOG = LoggerFactory.getLogger(CacheStatisticsServiceImpl.class);

    @Autowired
    private CacheManager cacheManager;

    public CacheStatistics getCacheStatistics(String cacheName){

        Cache teacherCache = cacheManager.getCache(cacheName);

        CacheStatistics cacheStatistics = getStatistics(teacherCache);

        return cacheStatistics;
    }


}
