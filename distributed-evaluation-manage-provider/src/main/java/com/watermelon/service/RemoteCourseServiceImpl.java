package com.watermelon.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.watermelon.api.entity.Course;
import com.watermelon.api.entity.User;
import com.watermelon.api.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class RemoteCourseServiceImpl {

    @Autowired
    private RestTemplate restTemplate;

    private static final String DEPENDENCY_URL = "http://distribute-course-manage-provider";

    /**
     * 依据班级ID获取班级的基本信息，该方法需要依赖distribute-course-manage-provider服务
     * <p>当distribute-course-manage-provider依赖出现故障时触发熔断机制
     * <p>熔断依赖：Class getClassById(int id)
     * @param id 班级号
     * @return aClass
     */
    @HystrixCommand(fallbackMethod = "getClassByIdFallback")
    public Class getClassById(int id){
        ResultUtil result = restTemplate.getForObject(DEPENDENCY_URL+"/class/getClassById?id="+id, ResultUtil.class);
        return (Class) result.getData();
    }

    /**
     * 当distribute-course-manage-provider依赖出现故障时调用该方法避免服务占用
     * <p>该方法返回一个null值，在不影响的系统整体正常运行情况下，提供有损的服务
     * @param id 班级号
     * @return null
     */
    public Class getClassByIdFallback(int id){
        return null;
    }


    @HystrixCommand(fallbackMethod = "listCourseByClassIdFallback")
    public IPage<Course> listCourseByClassId(int startPage, int pageSize, int classId){
        String url = DEPENDENCY_URL+"/courseManage/listCourseByClassIdForRemoteCall?startPage="+startPage+"&pageSize="+pageSize+"&classId="+classId;
        ParameterizedTypeReference<Page<Course>> typeRef = new ParameterizedTypeReference<Page<Course>>() {};
        ResponseEntity<Page<Course>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, typeRef);
        IPage<Course> list = responseEntity.getBody();
        List<Course> courseList = list.getRecords();
        return list;
    }

    /**
     * 当distribute-course-manage-provider依赖出现故障时调用该方法避免服务占用
     * <p>该方法返回一个null值，在不影响的系统整体正常运行情况下，提供有损的服务
     * @param startPage
     * @param pageSize
     * @param classId
     * @return
     */
    public IPage<Course> listCourseByClassIdFallback(int startPage, int pageSize, int classId){
        return null;
    }

    @HystrixCommand(fallbackMethod = "listCourseBySupervisorIdFallback")
    public IPage<Course> listCourseBySupervisorId(int startPage, int pageSize, int supervisorId){
        String url = "http://distribute-user-manage-provider/supervisor/listCourseForRemote?startPage="+startPage+"&pageSize="+pageSize+"&supervisorId="+supervisorId;
        ParameterizedTypeReference<Page<Course>> typeRef = new ParameterizedTypeReference<Page<Course>>() {};
        ResponseEntity<Page<Course>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, typeRef);
        IPage<Course> page = responseEntity.getBody();
        return page;
    }

    public IPage<Course> listCourseBySupervisorIdFallback(int startPage, int pageSize, int supervisorId){
        return null;
    }

    @HystrixCommand(fallbackMethod = "listCourseFallback")
    public IPage<Course> listCourse(int startPage, int pageSize){
        String url = DEPENDENCY_URL+"/courseManage/listCourseForRemote?startPage="+startPage+"&pageSize="+pageSize;
        ParameterizedTypeReference<Page<Course>> typeRef = new ParameterizedTypeReference<Page<Course>>() {};
        ResponseEntity<Page<Course>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, typeRef);
        IPage<Course> page = responseEntity.getBody();
        return page;
    }

    public IPage<Course> listCourseFallback(int startPage, int pageSize){
        return null;
    }


    @HystrixCommand(fallbackMethod = "listCourseByTeacherIdFallback")
    public IPage<Course> listCourseByTeacherId(int startPage, int pageSize, int teacherId){
        String url = DEPENDENCY_URL+"/courseManage/listCourseByTeacherIdForRemote?startPage="+startPage+"&pageSize="+pageSize+"&teacherId="+teacherId;
        ParameterizedTypeReference<Page<Course>> typeRef = new ParameterizedTypeReference<Page<Course>>() {};
        ResponseEntity<Page<Course>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, typeRef);
        IPage<Course> page = responseEntity.getBody();
        return page;
    }

    public IPage<Course> listCourseByTeacherIdFallback(int startPage, int pageSize, int teacherId){
        return null;
    }

}
