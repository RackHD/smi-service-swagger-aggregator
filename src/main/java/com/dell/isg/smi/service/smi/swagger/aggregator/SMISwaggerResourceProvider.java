/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.service.smi.swagger.aggregator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

@Component
@Primary
@EnableAutoConfiguration
public class SMISwaggerResourceProvider implements SwaggerResourcesProvider {

    @Autowired
    private SwaggerServicesConfig swaggerApiList;

	@Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resourcesList = new ArrayList<>();

        swaggerApiList.getServices().forEach(service -> {
        	resourcesList.add(createSwaggerResource(service.getName(),service.getUrl(), service.getVersion()));
        });
        
        return resourcesList;
    }

    private SwaggerResource createSwaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;	
    }
    

}