package com.watermelon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class UserManageServerApplication {

    public static void main(String[] args){
        SpringApplication.run(UserManageServerApplication.class,args);
    }

}
