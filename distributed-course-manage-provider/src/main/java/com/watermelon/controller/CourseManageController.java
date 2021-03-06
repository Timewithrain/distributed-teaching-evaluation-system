package com.watermelon.controller;

import com.watermelon.api.entity.Course;
import com.watermelon.api.service.CourseService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courseManage")
public class CourseManageController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/addCourse")
    public Map<String, String> addCourse(@RequestBody(required=false) Course course){
        courseService.addCourse(course);
        Map<String,String> map = new HashMap<>();
        map.put("status","200");
        map.put("massage","add course succeed");
        return map;
    }

    @PutMapping("/updateCourse")
    public Map<String, String> updateCourse(@RequestBody(required=false) Course course){
        courseService.updateCourse(course);
        Map<String,String> map = new HashMap<>();
        map.put("status","200");
        map.put("massage","update course succeed");
        return map;
    }

    @DeleteMapping("/deleteCourse/{id}")
    public Map<String, String> deleteCourse(@RequestParam(value="id",required=false) int id){
        courseService.deleteCourse(id);
        Map<String,String> map = new HashMap<>();
        map.put("status","200");
        map.put("massage","delete course succeed");
        return map;
    }

    @GetMapping("/listCourse")
    public List<Course> listCourse(int startPage, int pageSize){
        List<Course> list = courseService.listCourse(startPage, pageSize);
        return list;
    }

    @GetMapping("/listAllCourseByClassId")
    public List<Course> listAllCourseByClassId(int classId){
        List<Course> list = courseService.listAllCourseByClassId(classId);
        return list;
    }

    @GetMapping("/listCourseByClassId")
    public List<Course> listCourseByClassId(int startPage, int pageSize, int classId){
        List<Course> list = courseService.listCourseByClassId(startPage, pageSize, classId);
        return list;
    }

    @GetMapping("/listCourseByDepartmentId")
    public List<Course> listCourseByDepartmentId(@ApiParam(value="起始页",example="1") @RequestParam(value="startPage",required=false) int startPage,
                                                 @ApiParam(value="页数",example="5") @RequestParam(value="pageSize",required=false) int pageSize,
                                                 @ApiParam(value="学院号",example="1") @RequestParam(value="departmentId",required=false) int departmentId){
        List<Course> list = courseService.listCourseByDepartmentId(startPage, pageSize, departmentId);
        return list;
    }

    @GetMapping("/listCourseWithNoTeacher")
    public List<Course> listCourseWithoutTeacher(int startPage, int pageSize){
        List<Course> list = courseService.listCourseWithoutTeacher(startPage, pageSize);
        return list;
    }

    @GetMapping("/searchCourse")
    public List<Course> searchCourse(@ApiParam(value="起始页",example="1") @RequestParam(value="startPage",required=false) int startPage,
                                     @ApiParam(value="页数",example="5") @RequestParam(value="pageSize",required=false) int pageSize,
                                     @ApiParam(value="查询关键字",example="计算机") @RequestParam(value="str",required=false) String str){
        List<Course> list = courseService.searchCourse(startPage, pageSize,str);
        return list;
    }

}
