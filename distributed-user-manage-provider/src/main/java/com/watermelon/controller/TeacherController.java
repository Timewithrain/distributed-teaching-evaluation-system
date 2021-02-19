package com.watermelon.controller;

import com.watermelon.api.entity.Course;
import com.watermelon.api.entity.Teacher;
import com.watermelon.api.entity.User;
import com.watermelon.api.service.TeacherService;
import com.watermelon.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private UserService userService;

    @Autowired
    private TeacherService teacherService;

    @ResponseBody
    @GetMapping("/getTeacher")
    public Teacher getTeacher(HttpSession session){
        String username = (String) session.getAttribute("username");
        User user = userService.getUserByName(username);
        return teacherService.getTeacherById(user.getId());
    }

    @PutMapping("/updateTeacher")
    public void updateTeacher(@RequestBody(required=false) Teacher teacher) {
        teacherService.updateTeacher(teacher);
    }

    @GetMapping("/listCourse")
    public List<Course> listCourseByTeacherId(int startPage, int pageSize, int teacherId){
        List<Course> list = teacherService.listCourseByTeacherId(startPage, pageSize, teacherId);
        return list;
    }

}
