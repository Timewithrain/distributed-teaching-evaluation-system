package com.watermelon.controller;

import com.watermelon.api.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String PROVIDER_URL = "http://distribute-user-manage-provider/student";

    @GetMapping("/getStudent")
    public Student getTeacher(){
        return restTemplate.getForObject(PROVIDER_URL + "/getTeacher",Student.class);
    }

    /**
     * 修改学生的对应教师
     * @param student
     */
    @PutMapping("/updateStudent")
    public void updateTeacher(@RequestBody(required=false) Student student) {
        restTemplate.put(PROVIDER_URL+"/updateStudent",student, Map.class);
    }

}
