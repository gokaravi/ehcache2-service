package com.ravi.cache.service.impl;

import com.ravi.dto.CacheStatistics;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

import static com.ravi.constants.CacheConstants.*;

/**
 * Created by NIS1563-MBP on 12/6/18.
 */


public class BaseCacheStatisticsServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(BaseCacheStatisticsServiceImpl.class);


    protected CacheStatistics getStatistics(Cache cache) {
        CacheStatistics cacheStatistics = null;
        try {
            if (null != cache) {
                ObjectName objectName = getJMXObjectName(cache);
                MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
                // retrieving the cache statistics from MBeanServer.
                cacheStatistics = mapStatistics(mBeanServer, objectName);
                cacheStatistics.setAverageGetTime(cache.getStatistics().getAverageGetTime());
            }
        } catch (Exception ex) {
            LOG.error("ERROR :: Exception occurred while getting the statistics for cache {} and the exception {}",
                    cache, ex);
            throw new RuntimeException(ex);
        }
        return cacheStatistics;
    }

    private ObjectName getJMXObjectName(Cache cache){
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

        String cacheManagerName = cache.getCacheManager().getName();

        String cacheName = sanitize(cache.getName());
        ObjectName objectName;
        try {
            objectName = new ObjectName("net.sf.ehcache:type="+CACHE_STATISTICS_BEAN+",CacheManager="+cacheManagerName+",name=" + cacheName);
        }
        catch (MalformedObjectNameException e) {
            throw new CacheException(e);
        }

        if(!mBeanServer.isRegistered(objectName)){
            throw new CacheException("No MBean found with ObjectName => " + objectName.getCanonicalName());
        }

        return objectName;
    }

    private String sanitize(String string) {
        return ((string == null) ? "" : string.replaceAll(",|:|=|\n", "."));
    }

    private CacheStatistics mapStatistics(MBeanServer mBeanServer, ObjectName objectName){
        CacheStatistics cacheStatistics = new CacheStatistics();
        try {
            cacheStatistics.setCacheHits((long)mBeanServer.getAttribute(objectName, CACHE_HITS));
            cacheStatistics.setCacheHitPercentage((double)mBeanServer.getAttribute(objectName, CACHE_HIT_PERCENTAGE));
            cacheStatistics.setCacheMisses((long)mBeanServer.getAttribute(objectName, CACHE_MISSES));
            cacheStatistics.setCacheMissPercentage((double)mBeanServer.getAttribute(objectName, CACHE_MISS_PERCENTAGE));
            //cacheStatistics.setCacheGets((long)mBeanServer.getAttribute(objectName, CACHE_GETS));
            //cacheStatistics.setCachePuts((long)mBeanServer.getAttribute(objectName, CACHE_PUTS));
            //cacheStatistics.setCacheRemovals((long)mBeanServer.getAttribute(objectName, CACHE_REMOVALS));
            //cacheStatistics.setCacheEvictions((long)mBeanServer.getAttribute(objectName, CACHE_EVICTIONS));
            //cacheStatistics.setAverageGetTime((float)mBeanServer.getAttribute(objectName, CACHE_AVERAGE_GET_TIME));
            //cacheStatistics.setAveragePutTime((float)mBeanServer.getAttribute(objectName, CACHE_AVERAGE_PUT_TIME));
            //cacheStatistics.setAverageRemoveTime((float)mBeanServer.getAttribute(objectName, CACHE_AVERAGE_REMOVE_TIME));
        }
        catch (Exception ex){
            LOG.error("Exception occurred while reading the cache statistics from Eh107CacheStatisticsMXBean", ex);
        }
        return cacheStatistics;
    }
}
