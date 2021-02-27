package com.watermelon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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

    /**
     * 根据班级id获取班级课程列表，该方法为对distribute-course-manage-provider服务的资源调用
     * <p>当distribute-course-manage-provider依赖出现故障时触发熔断机制
     * <p>熔断依赖：null listCourseByClassIdFallback(int id)
     * @param id 班级id
     * @return List<Course> 课程id对应的班级列表
     */
    @HystrixCommand(fallbackMethod = "listCourseByClassIdFallback")
    List<Course> listCourseByClassId(int id){
        return restTemplate.getForObject(DEPENDENCY_URL+"/listAllCourseByClassId?classId="+id,List.class);
    }

    /**
     * 当distribute-course-manage-provider依赖出现故障时调用该方法避免服务占用
     * <p>该方法返回一个null值，在不影响的系统整体正常运行情况下，提供有损的服务
     * @param id 班级id
     * @return null
     */
    List<Course> listCourseByClassIdFallback(int id){
        return null;
    }

}
