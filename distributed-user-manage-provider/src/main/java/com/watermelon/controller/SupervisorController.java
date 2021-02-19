package com.watermelon.controller;

import com.watermelon.api.entity.Course;
import com.watermelon.api.entity.Supervisor;
import com.watermelon.api.entity.User;
import com.watermelon.api.service.SupervisorService;
import com.watermelon.api.service.UserService;
import com.watermelon.api.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/supervisor")
@RestController
public class SupervisorController {

    @Autowired
    private UserService userService;

    @Autowired
    private SupervisorService supervisorService;

    @Deprecated
    @GetMapping("/listSupervisor")
    public Object listSupervisor(){
        return ResultUtil.success(supervisorService.getAllSupervisor());
    }

    @GetMapping("/getSupervisor")
    public Supervisor getSupervisor(HttpSession session){
        String username = (String) session.getAttribute("username");
        User user = userService.getUserByName(username);
        return supervisorService.getSupervisorById(user.getId());
    }

    @PutMapping("/updateSupervisor")
    public Map<String, String> updateSupervisor(@RequestBody(required=false) Supervisor supervisor) {
        supervisorService.updateSupervisor(supervisor);
        Map<String,String> map = new HashMap<>();
        map.put("status","200");
        map.put("message","修改督导成功");
        return map;
    }

    // 设置督导可评价的课程
    @PostMapping("/addCourse")
    public Object addSupervisorCourse(int supervisorId, int courseId, int teacherId){
        supervisorService.addSupervisorCourse(supervisorId,courseId,teacherId);
        return ResultUtil.success();
    }

    @DeleteMapping("/deleteCourse")
    public Object deleteSupervisorCourse(int supervisorId, int courseId, int teacherId){
        supervisorService.deleteSupervisorCourse(supervisorId,courseId,teacherId);
        return ResultUtil.success();
    }

    @GetMapping("/listCourse")
    public List<Course> listCourseBySupervisorId(int startPage, int pageSize, int supervisorId){
        List<Course> list = supervisorService.listCourseBySupervisorId(startPage, pageSize, supervisorId);
        return list;
    }

}
