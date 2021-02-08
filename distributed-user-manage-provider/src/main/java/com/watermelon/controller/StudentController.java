package com.watermelon.controller;

import com.watermelon.api.entity.Student;
import com.watermelon.api.entity.User;
import com.watermelon.api.service.StudentService;
import com.watermelon.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @ResponseBody
    @GetMapping("/getTeacher")
    public Student getTeacher(HttpSession session){
        String username = (String) session.getAttribute("username");
        User user = userService.getUserByName(username);
        return studentService.getStudentById(user.getId());
    }

    @PutMapping("/updateTeacher")
    public void updateTeacher(@RequestBody(required=false) Student student) {
        studentService.updateStudent(student);
    }

}
