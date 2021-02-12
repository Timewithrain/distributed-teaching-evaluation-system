package com.watermelon.controller;

import com.watermelon.api.service.SupervisorService;
import com.watermelon.api.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/supervisor")
@RestController
public class SupervisorController {

    @Autowired
    private SupervisorService supervisorService;

    @GetMapping("/listSupervisor")
    public Object listSupervisor(){
        return ResultUtil.success(supervisorService.getAllSupervisor());
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
}
