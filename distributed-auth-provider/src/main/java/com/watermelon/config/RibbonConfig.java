package com.watermelon.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Ribbon代理客户端基本设置
 * <p>负载均衡方式：轮询
 */
@Configuration
public class RibbonConfig {

    @Bean
    @LoadBalanced   //通过ribbon实现负载均衡
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

}
