package com.ravi.cache.service.impl;

import static com.ravi.constants.CacheConstants.TEACHER_CACHE_ALIAS;
import static com.ravi.constants.CacheConstants.TEACHER_CACHE_KEY;


import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ravi.cache.service.TeacherCacheService;
import com.ravi.cache.service.TeacherService;
import com.ravi.dto.Teachers;

/**
 * Created by Ravi Goka on 11/18/2018.
 */

@Component
public class TeacherCacheServiceImpl implements TeacherCacheService {
    private static final Logger LOG = LoggerFactory.getLogger(TeacherCacheServiceImpl.class);

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CacheManager cacheManager;

    @Override
    public Teachers getAllTeachers(){
        if(LOG.isDebugEnabled()){
            LOG.debug("START :: Getting all teachers");
        }
        Teachers teachers;
        Cache teacherCache = cacheManager.getCache(TEACHER_CACHE_ALIAS);
        Element teacherElement = teacherCache.get(TEACHER_CACHE_KEY);
        if(null != teacherElement){
            teachers = (Teachers)teacherElement.getObjectValue();
        }
        else{
            if(LOG.isDebugEnabled()){
                LOG.debug("Data Not found in Cache: {}", teacherElement);
                LOG.debug("Reading user information from service/DB");
            }
            teachers = teacherService.getAllTeachers();
            teacherCache.put(new Element(TEACHER_CACHE_KEY, teachers));
        }
        if(LOG.isDebugEnabled()){
            LOG.debug("END :: Getting all teachers: {}", teachers);
        }
        return teachers;
    }
}
