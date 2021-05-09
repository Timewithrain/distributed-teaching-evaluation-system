package com.watermelon.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.watermelon.api.entity.Course;
import com.watermelon.api.service.CourseService;
import com.watermelon.api.util.ResultUtil;
import com.watermelon.mapper.CourseMapper;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/courseManage")
public class CourseManageController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseMapper courseMapper;

    @PostMapping("/addCourse")
    public ResultUtil addCourse(@RequestBody(required=false) Course course){
        courseService.addCourse(course);
        return ResultUtil.success(null,"add course succeed");
    }

    @PutMapping("/updateCourse")
    public ResultUtil updateCourse(@RequestBody(required=false) Course course){
        courseService.updateCourse(course);
        return ResultUtil.success(null,"update course succeed");
    }

    @DeleteMapping("/deleteCourse")
    public ResultUtil deleteCourse(@RequestParam(value="id",required=false) int id){
        courseService.deleteCourse(id);
        return ResultUtil.success(null,"delete course succeed");
    }

    @GetMapping("/listCourse")
    public ResultUtil listCourse(int startPage, int pageSize){
        IPage<Course> list = courseService.listCourse(startPage, pageSize);
        Map<String,Object> map = new HashMap<>();
        map.put("list",list.getRecords());
        map.put("total",list.getTotal());
        return ResultUtil.success(map);
    }

    @GetMapping("/listCourseForRemote")
    public Object listCourseForRemote(int startPage, int pageSize){
        IPage<Course> list = courseService.listCourse(startPage, pageSize);
        return list;
    }

    @GetMapping("/listAllCourse")
    public ResultUtil listAllCourse(){
        List<Course> list = courseService.listAllCourse();
        return ResultUtil.success(list);
    }

    @GetMapping("/listAllCourseByClassId")
    public ResultUtil listAllCourseByClassId(int classId){
        List<Course> list = courseService.listAllCourseByClassId(classId);
        return ResultUtil.success(list);
    }

    @GetMapping("/listAllCourseByClassIdForRemote")
    public Object listAllCourseByClassIdForRemote(int classId){
        return courseService.listAllCourseByClassId(classId);
    }

    @GetMapping("/listCourseByClassId")
    public ResultUtil listCourseByClassId(int startPage, int pageSize, int classId){
        IPage<Course> list = courseService.listCourseByClassId(startPage, pageSize, classId);
        Map<String,Object> map = new HashMap<>();
        map.put("list",list.getRecords());
        map.put("total",list.getTotal());
        return ResultUtil.success(map);
    }

    @GetMapping("/listCourseByTeacherId")
    public Object listCourseByTeacherId(int startPage, int pageSize, int teacherId){
        IPage<Course> list = courseService.listCourseByTeacherId(startPage, pageSize, teacherId);
        Map<String,Object> map = new HashMap<>();
        map.put("list",list.getRecords());
        map.put("total",list.getTotal());
        return ResultUtil.success(map);
    }

    @GetMapping("/listCourseByTeacherIdForRemote")
    public Object listCourseByTeacherIdForRemote(int startPage, int pageSize, int teacherId){
        IPage<Course> page = courseService.listCourseByTeacherId(startPage, pageSize, teacherId);
        return page;
    }

    @GetMapping("/listCourseByClassIdForRemoteCall")
    public Object listCourseByClassIdForRemoteCall(int startPage, int pageSize, int classId){
        IPage<Course> page = courseService.listCourseByClassId(startPage, pageSize, classId);
        return page;
    }

    @GetMapping("/listCourseByDepartmentId")
    public ResultUtil listCourseByDepartmentId(@ApiParam(value="起始页",example="1") @RequestParam(value="startPage",required=false) int startPage,
                                                 @ApiParam(value="页数",example="5") @RequestParam(value="pageSize",required=false) int pageSize,
                                                 @ApiParam(value="学院号",example="1") @RequestParam(value="departmentId",required=false) int departmentId){
        IPage<Course> list = courseService.listCourseByDepartmentId(startPage, pageSize, departmentId);
        Map<String,Object> map = new HashMap<>();
        map.put("list",list.getRecords());
        map.put("total",list.getTotal());
        return ResultUtil.success(map);
    }

    @GetMapping("/listCourseWithNoTeacher")
    public ResultUtil listCourseWithoutTeacher(int startPage, int pageSize){
        IPage<Course> list = courseService.listCourseWithoutTeacher(startPage, pageSize);
        Map<String,Object> map = new HashMap<>();
        map.put("list",list.getRecords());
        map.put("total",list.getTotal());
        return ResultUtil.success(map);
    }

    @GetMapping("/searchCourseWithNoTeacher")
    public ResultUtil searchCourseWithNoTeacher(@ApiParam(value="起始页",example="1") @RequestParam(value="startPage",required=false) int startPage,
                                     @ApiParam(value="页数",example="5") @RequestParam(value="pageSize",required=false) int pageSize,
                                     @ApiParam(value="查询关键字",example="计算机") @RequestParam(value="str",required=false) String str,
                                     @ApiParam(value="课程类型",example="必修课") @RequestParam(value="courseClass",required=false) String courseClass,
                                     @ApiParam(value="学院号",example="1") @RequestParam(value="departmentId",required=false) String departmentId){
        IPage<Course> list = courseService.searchCourseWithoutTeacher(startPage, pageSize, str, courseClass, departmentId);
        Map<String,Object> map = new HashMap<>();
        map.put("list",list.getRecords());
        map.put("total",list.getTotal());
        return ResultUtil.success(map);
    }

    @GetMapping("/searchCourseNotSelected")
    public ResultUtil searchCourseNotSelected(@ApiParam(value="起始页",example="1") @RequestParam(value="startPage",required=false) int startPage,
                                   @ApiParam(value="页数",example="5") @RequestParam(value="pageSize",required=false) int pageSize,
                                   @ApiParam(value="查询关键字",example="计算机") @RequestParam(value="str",required=false) String str,
                                   @ApiParam(value="课程号",example="0") @RequestParam(value="classId",required=false) int classId){
        IPage<Course> list = courseService.searchCourseNotSelected(startPage, pageSize, str, classId);
        Map<String,Object> map = new HashMap<>();
        map.put("list",list.getRecords());
        map.put("total",list.getTotal());
        return ResultUtil.success(map);
    }

    @GetMapping("/searchCourseBySupervisorId")
    public ResultUtil searchCourseBySupervisorId(@ApiParam(value="起始页",example="1") @RequestParam(value="startPage",required=false) int startPage,
                                              @ApiParam(value="页数",example="5") @RequestParam(value="pageSize",required=false) int pageSize,
                                              @ApiParam(value="查询关键字",example="计算机") @RequestParam(value="str",required=false) String str,
                                              @ApiParam(value="督导Id",example="0") @RequestParam(value="supervisorId",required=false) String supervisorId,
                                              @ApiParam(value="班级号",example="0") @RequestParam(value="classId",required=false) String classId){
        IPage<Course> list = courseService.searchCourseBySupervisorId(startPage, pageSize, supervisorId, classId, str);
        Map<String,Object> map = new HashMap<>();
        map.put("list",list.getRecords());
        map.put("total",list.getTotal());
        return ResultUtil.success(map);
    }

}
