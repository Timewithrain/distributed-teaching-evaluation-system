package com.watermelon.service;

import com.watermelon.api.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RemoteCourseService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String DEPENDENCY_URL = "http://distribute-course-manage-provider/courseManage";

    List<Course> listCourseByClassId(int id){

        return null;
    }

}
