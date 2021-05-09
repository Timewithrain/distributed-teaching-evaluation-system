package com.watermelon.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.watermelon.api.entity.Course;
import com.watermelon.api.entity.Teacher;
import com.watermelon.api.entity.User;
import com.watermelon.api.service.TeacherService;
import com.watermelon.api.service.UserService;
import com.watermelon.api.util.ResultUtil;
import com.watermelon.api.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private UserService userService;

    @Autowired
    private TeacherService teacherService;

    @ResponseBody
    @GetMapping("/getTeacher")
    public ResultUtil getTeacher(HttpServletRequest request){
        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute("id");
        if (id != null) {
            Teacher teacher = teacherService.getTeacherById(id);
            return ResultUtil.success(teacher);
        } else {
            return ResultUtil.error(StatusCode.EXCEPTION_ERROR);
        }
    }

    @PutMapping("/updateTeacher")
    public void updateTeacher(@RequestBody(required=false) Teacher teacher) {
        teacherService.updateTeacher(teacher);
    }

    @GetMapping("/listCourse")
    public ResultUtil listCourseByTeacherId(int startPage, int pageSize, int teacherId){
        IPage<Course> list = teacherService.listCourseByTeacherId(startPage, pageSize, teacherId);
        Map<String,Object> map = new HashMap<>();
        map.put("list",list.getRecords());
        map.put("total",list.getTotal());
        return ResultUtil.success(map);
    }

}
