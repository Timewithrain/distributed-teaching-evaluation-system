package com.watermelon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableEurekaClient
@EnableRedisHttpSession
@EnableCircuitBreaker
public class UserManageServerApplication {

    public static void main(String[] args){
        SpringApplication.run(UserManageServerApplication.class,args);
    }

}
