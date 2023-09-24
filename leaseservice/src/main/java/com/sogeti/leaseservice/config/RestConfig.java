package com.sogeti.leaseservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * RestConfig class.
 * This class is used to configure RestTemplate beans.
 * @Author:revathi
 */
@Configuration
public class RestConfig {

    /**
     * This method is used to create customerRestTemplate bean
     * @return
     */
    @Bean("customerRestTemplate")
    public RestTemplate customerRestTemplate() {
        return new RestTemplate();
    }

    /**
     * This method is used to create carRestTemplate bean
     * @return
     */
    @Bean("carRestTemplate")
    public RestTemplate carRestTemplate() {
        return new RestTemplate();
    }
}

