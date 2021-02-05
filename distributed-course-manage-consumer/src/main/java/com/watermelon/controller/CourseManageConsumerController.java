package com.watermelon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/courseManage")
public class CourseManageConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String PROVIDER_URL = "http://distribute-course-manage-provider";

    @GetMapping("/listCourse/")
    public List listCourse(int pageSize, int startPage){
        String params = "?pageSize=" + pageSize + "&startPage=" + startPage;
        return restTemplate.getForObject(PROVIDER_URL+"/courseManage/listCourseWithNoTeacher"+params,List.class);
    }

}
