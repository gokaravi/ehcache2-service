package com.ravi;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.management.ManagementService;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.Resource;

import javax.management.MBeanServer;
import java.lang.management.ManagementFactory;
import java.net.URL;


/**
 * Created by Ravi Goka on 11/18/2018.
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = {"com.ravi"})
public class AppConfig extends SpringBootServletInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(AppConfig.class);

    @Value("classpath:ehcache-config.xml")
    private Resource cacheResource;

    public static void main(String[] args) {
        new AppConfig().configure(new SpringApplicationBuilder(AppConfig.class)).run(args);
    }

    @Bean
    ResourceConfig resourceConfig() {
        return new ResourceConfig().packages("com.ravi");
    }

    @Bean(name="ehCacheManager")
    public CacheManager cacheManager() {
        CacheManager cacheManager = null;
        try {
            URL url = cacheResource.getURL();
            cacheManager = new CacheManager(url);
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            ManagementService.registerMBeans(cacheManager, mBeanServer, true, true, false, true);

        }
        catch(Exception ioe){
            LOG.error("ERROR :: Error occurred while initializing cache manager. Cannot load the ehcache.xml file.", ioe);
        }
        return cacheManager;
    }

}
