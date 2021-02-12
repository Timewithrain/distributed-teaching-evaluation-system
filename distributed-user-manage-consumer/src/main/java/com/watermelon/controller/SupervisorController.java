package com.watermelon.controller;

import com.watermelon.api.entity.Supervisor;
import com.watermelon.api.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/supervisor")
@RestController
public class SupervisorController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String PROVIDER_URL = "http://distribute-user-manage-provider/supervisor";

    @GetMapping("/listSupervisor")
    public Object listSupervisor(){
        return restTemplate.getForObject(PROVIDER_URL+"/listSupervisor", ResultUtil.class);
    }

    // 设置督导可评价的课程
    @PostMapping("/addCourse")
    public Object addSupervisorCourse(int supervisorId, int courseId, int teacherId){
        String url = PROVIDER_URL + "/addCourse?supervisorId={supervisorId}&courseId={courseId}&teacherId={teacherId}";
        Map<String,Integer> params = new HashMap();
        params.put("supervisorId",supervisorId);
        params.put("courseId",courseId);
        params.put("teacherId",teacherId);
        ResponseEntity<ResultUtil> entity = restTemplate.postForEntity(url,null,ResultUtil.class,params);
        return entity.getBody();
    }
    @DeleteMapping("/deleteCourse")
    public Object deleteSupervisorCourse(int supervisorId, int courseId, int teacherId){
        String url = PROVIDER_URL + "/deleteCourse?supervisorId={supervisorId}&courseId={courseId}&teacherId={teacherId}";
        Map<String,Integer> params = new HashMap();
        params.put("supervisorId",supervisorId);
        params.put("courseId",courseId);
        params.put("teacherId",teacherId);
        restTemplate.delete(url,params);
        return ResultUtil.success();
    }
}
