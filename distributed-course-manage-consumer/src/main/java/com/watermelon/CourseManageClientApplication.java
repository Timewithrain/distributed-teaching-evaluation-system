package com.watermelon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CourseManageClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseManageClientApplication.class,args);
    }

}
