package com.watermelon.config;

import com.watermelon.filter.AccessFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class ZuulFilterConfig {

//    @Bean
    public AccessFilter redisSessionFilter() {
        return new AccessFilter();
    }

}
