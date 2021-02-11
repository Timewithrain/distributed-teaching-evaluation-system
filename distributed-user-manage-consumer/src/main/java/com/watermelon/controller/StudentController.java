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

    @ResponseBody
    @GetMapping("/getTeacher")
    public Student getTeacher(){
        return restTemplate.getForObject(PROVIDER_URL + "/getTeacher",Student.class);
    }

    /**
     * 修改学生的对应教师
     * @param student
     */
    @PutMapping("/updateTeacher")
    public void updateTeacher(@RequestBody(required=false) Student student) {
        restTemplate.put(PROVIDER_URL+"/updateTeacher",student, Map.class);
    }

}
