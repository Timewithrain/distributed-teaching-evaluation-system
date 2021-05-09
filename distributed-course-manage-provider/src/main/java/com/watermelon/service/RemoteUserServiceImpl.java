package com.watermelon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.watermelon.api.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 远程User资源调用服务
 * 远程调用的方式对Student资源进行操作，以降低系统服务之间的耦合度。
 */
@Service
public class RemoteUserServiceImpl {

    @Autowired
    private RestTemplate restTemplate;

    private static final String DEPENDENCY_URL = "http://distribute-user-manage-provider";

    /**
     * 远程调用distribute-user-manage-provider模块中的deleteStudentByClassId方法
     * 通过班级号删除学生
     * @param classId
     */
    @HystrixCommand(fallbackMethod = "deleteStudentByClassIdFallback")
    public void deleteStudentByClassId(int classId){
        restTemplate.delete(DEPENDENCY_URL+"/admin/deleteStudentByClassId?classId="+classId);
    }

    /**
     * 当distribute-user-manage-provider依赖出现故障时调用该方法避免服务占用
     * <p>该方法抛出一个异常，在不影响的系统整体正常运行情况下，提供有损的服务
     * @param classId 班级id
     */
    private void deleteStudentByClassIdFallback(int classId) {
        throw new RuntimeException("用户资源服务出现故障，未能删除课程号为"+classId+"的课程！");
    }

    /**
     * 远程调用distribute-user-manage-provider模块中的deleteTeacherByDepartmentId方法
     * 通过学院号删除教师
     * @param departmentId
     */
    @HystrixCommand(fallbackMethod = "deleteTeacherByDepartmentIdFallback")
    public void deleteTeacherByDepartmentId(int departmentId){
        restTemplate.delete(DEPENDENCY_URL+"/admin/deleteTeacherByDepartmentId?id="+departmentId);
    }

    private void deleteTeacherByDepartmentIdFallback(int departmentId) {
        throw new RuntimeException("用户资源服务出现故障，未能删除学院号为"+departmentId+"的教师！");
    }
}
