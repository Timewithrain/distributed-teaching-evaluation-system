package com.watermelon.controller;

import com.watermelon.api.entity.Course;
import com.watermelon.api.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String PROVIDER_URL = "http://distribute-user-manage-provider/teacher";

    @GetMapping("/getTeacher")
    public Teacher getTeacher(){
        return restTemplate.getForObject(PROVIDER_URL + "/getTeacher", Teacher.class);
    }

    @PutMapping("/updateTeacher")
    public void updateTeacher(@RequestBody(required=false) Teacher teacher) {
        restTemplate.put(PROVIDER_URL+"/updateTeacher",teacher, Map.class);
    }

    @GetMapping("/listCourse")
    public List<Course> listCourseByTeacherId(int startPage, int pageSize, int teacherId){
        String url = PROVIDER_URL + "/listCourse?startPage="+startPage+"&pageSize="+pageSize+"&teacherId="+teacherId;
        return restTemplate.getForObject(url,List.class);
    }

}
