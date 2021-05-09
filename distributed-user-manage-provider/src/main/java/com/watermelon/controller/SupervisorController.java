package com.watermelon.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.watermelon.api.entity.Course;
import com.watermelon.api.entity.Supervisor;
import com.watermelon.api.entity.User;
import com.watermelon.api.service.SupervisorService;
import com.watermelon.api.service.UserService;
import com.watermelon.api.util.ResultUtil;
import com.watermelon.api.util.StatusCode;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
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
    public ResultUtil getSupervisor(HttpServletRequest request){
        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute("id");
        if (id!=null) {
            Supervisor supervisor = supervisorService.getSupervisorById(id);
            return ResultUtil.success(supervisor);
        } else {
            return ResultUtil.error(StatusCode.EXCEPTION_ERROR);
        }
    }

    @PutMapping("/updateSupervisor")
    public ResultUtil updateSupervisor(@RequestBody(required=false) Supervisor supervisor) {
        supervisorService.updateSupervisor(supervisor);
        return ResultUtil.success(null, "修改督导成功");
    }

    // 设置督导可评价的课程
    @GetMapping("/addCourse")
    public ResultUtil addSupervisorCourse(Integer supervisorId, Integer courseId, Integer classId){
        supervisorService.addSupervisorCourse(supervisorId,courseId,classId);
        return ResultUtil.success();
    }

    @GetMapping("/addClass")
    public ResultUtil addSupervisorClass(Integer supervisorId, Integer classId){
        supervisorService.addSupervisorClass(supervisorId,classId);
        return ResultUtil.success();
    }

    @DeleteMapping("/deleteCourse")
    public ResultUtil deleteSupervisorCourse(Integer supervisorId, Integer courseId, Integer classId){
        supervisorService.deleteSupervisorCourse(supervisorId,courseId,classId);
        return ResultUtil.success();
    }

    @GetMapping("/listCourse")
    public ResultUtil listCourseBySupervisorId(int startPage, int pageSize, int supervisorId){
        IPage<Course> list = supervisorService.listCourseBySupervisorId(startPage, pageSize, supervisorId);
        Map<String, Object> map = new HashMap<>();
        map.put("list",list.getRecords());
        map.put("total",list.getTotal());
        return ResultUtil.success(map);
    }

    @GetMapping("/listCourseForRemote")
    public Object listCourseBySupervisorIdForRemote(int startPage, int pageSize, int supervisorId){
        IPage<Course> page = supervisorService.listCourseBySupervisorId(startPage, pageSize, supervisorId);
        return page;
    }

}
