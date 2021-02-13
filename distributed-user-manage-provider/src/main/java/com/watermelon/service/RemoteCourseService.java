package com.watermelon.service;

import com.watermelon.api.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 远程Course资源调用服务
 * 在StudentService中生成Student列表资源需要获取学生的课程信息，
 * 由于课程资源由course-manage-provider服务独立维护，因此采用
 * 远程调用的方式获取Course列表资源，以降低系统服务之间的耦合度。
 */
@Service
public class RemoteCourseService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String DEPENDENCY_URL = "http://distribute-course-manage-provider/courseManage";

    List<Course> listCourseByClassId(int id){
        return restTemplate.getForObject(DEPENDENCY_URL+"/listAllCourseByClassId?classId="+id,List.class);
    }

}
