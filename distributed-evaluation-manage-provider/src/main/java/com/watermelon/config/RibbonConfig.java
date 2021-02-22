package com.watermelon.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RibbonConfig {

    @Bean
    @LoadBalanced   //通过ribbon实现负载均衡
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

}
