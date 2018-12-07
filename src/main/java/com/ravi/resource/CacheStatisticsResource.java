package com.ravi.resource;


import com.ravi.cache.service.CacheStatisticsService;
import com.ravi.dto.CacheStatistics;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/cache/v1")
public class CacheStatisticsResource {

    @Autowired
    private CacheStatisticsService cacheStatisticsService;

    @GET
    @Path("{cacheName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCacheDetails(@PathParam("cacheName") String cacheName){

       CacheStatistics statistics =  cacheStatisticsService.getCacheStatistics(cacheName);
       return Response.status(Response.Status.OK).entity(statistics).build();

    }
}
