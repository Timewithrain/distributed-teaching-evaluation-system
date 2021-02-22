package com.watermelon.controller;

import com.watermelon.api.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String PROVIDER_URL = "http://distribute-user-manage-provider/student";

    @GetMapping("/getStudent")
    public Student getStudent(){
        return restTemplate.getForObject(PROVIDER_URL + "/getStudent",Student.class);
    }

    /**
     * 修改学生的对应教师
     * @param student
     */
    @PutMapping("/updateStudent")
    public void updateStudent(@RequestBody(required=false) Student student) {
        restTemplate.put(PROVIDER_URL+"/updateStudent",student, Map.class);
    }

}
