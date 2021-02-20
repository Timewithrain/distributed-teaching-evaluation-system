package com.watermelon.controller;

import com.watermelon.api.entity.Student;
import com.watermelon.api.entity.User;
import com.watermelon.api.service.StudentService;
import com.watermelon.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/getStudent")
    public Student getStudent(HttpServletRequest request){
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        System.out.println("========="+username+"=========");
        System.out.println("========="+session.getId()+"=========");
        System.out.println();
        if (username!=null){
            User user = userService.getUserByName(username);
            return studentService.getStudentById(user.getId());
        }else {
            return null;
        }
    }

    @PutMapping("/updateStudent")
    public void updateTeacher(@RequestBody(required=false) Student student) {
        studentService.updateStudent(student);
    }

}
