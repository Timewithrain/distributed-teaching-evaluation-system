package com.watermelon.controller;

import com.watermelon.api.entity.Student;
import com.watermelon.api.entity.User;
import com.watermelon.api.service.StudentService;
import com.watermelon.api.service.UserService;
import com.watermelon.api.util.ResultUtil;
import com.watermelon.api.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@CrossOrigin
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/getStudent")
    public ResultUtil getStudent(HttpServletRequest request){
        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute("id");
        if (id!=null){
            Student student = studentService.getStudentById(id);
            return ResultUtil.success(student);
        }else {
            return ResultUtil.error(StatusCode.EXCEPTION_ERROR);
        }
    }

    @PutMapping("/updateStudent")
    public void updateStudent(@RequestBody(required=false) Student student) {
        studentService.updateStudent(student);
    }

}
