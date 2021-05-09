package com.watermelon.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.watermelon.api.entity.Class;
import com.watermelon.api.service.ClassService;
import com.watermelon.api.util.ResultUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/class")
public class ClassController {

    @Autowired
    private ClassService classService;

    @GetMapping("/getClassById")
    public ResultUtil getClassById(int id) {
        Class aClass = classService.getClassById(id);
        return ResultUtil.success(aClass);
    }

    @ApiOperation(value="添加班级(包括Department,不包括Course信息)")
    @PostMapping("/addClass")
    public ResultUtil addClass(@RequestBody(required=false)Class aClass){
        classService.addClass(aClass);
        return ResultUtil.success(null,"add class succeed");
    }

    @ApiOperation(value="更新班级信息(包括Department,不包括Course信息)")
    @PutMapping("/updateClass")
    public ResultUtil updateClass(@RequestBody(required=false) Class aClass){
        classService.updateClass(aClass);
        return ResultUtil.success(null,"update class succeed");
    }

    @ApiOperation(value="删除班级(级联删除)")
    @DeleteMapping("/deleteClass")
    public ResultUtil deleteClass(@RequestParam(value = "classId",required=false) int classId){
        classService.deleteClass(classId);
        return ResultUtil.success(null,"delete class succeed");
    }

    @ApiOperation(value="为班级添加课程")
    @GetMapping("/addClassCourse")
    public ResultUtil addClassCourse(
            @ApiParam(value="班级号",example="1") @RequestParam(value="classId",required=false) int classId,
            @ApiParam(value="课程号",example="1") @RequestParam(value="courseId",required=false) int courseId,
            @ApiParam(value="教师号",example="1") @RequestParam(value="teacherId",required=false) int teacherId){
        classService.addClassCourse(classId, courseId, teacherId);
        return ResultUtil.success(null,"add a course of the class successfully");
    }

    @ApiOperation(value="删除班级的对应课程")
    @DeleteMapping("/deleteClassCourse")
    public ResultUtil deleteClassCourse(
            @ApiParam(value="班级号",example="1") @RequestParam(value="classId",required=false) int classId,
            @ApiParam(value="课程号",example="1") @RequestParam(value="courseId",required=false)int courseId){
        classService.deleteClassCourse(classId, courseId);
        return ResultUtil.success(null,"delete a course of the class successfully");
    }

    @ApiOperation(value="获取班级列表(包括课程信息)")
    @GetMapping("/listClass")
    public ResultUtil listClass(int startPage, int pageSize){
        IPage<Class> list = classService.listClass(startPage, pageSize);
        Map<String,Object> map = new HashMap<>();
        map.put("list",list.getRecords());
        map.put("total",list.getTotal());
        return ResultUtil.success(map);
    }

    @ApiOperation(value="获取班级列表(不包括课程信息)")
    @GetMapping("/listClassWithNoCourse")
    public ResultUtil listClassWithNoCourse(int startPage, int pageSize){
        IPage<Class> list = classService.listClassWithNoCourse(startPage, pageSize);
        Map<String,Object> map = new HashMap<>();
        map.put("list",list.getRecords());
        map.put("total",list.getTotal());
        return ResultUtil.success(map);
    }

    @ApiOperation(value="根据学院号获取全部班级列表(不包括课程信息，不分页)")
    @GetMapping("/listAllClassWithNoCourseByDepartmentId")
    public ResultUtil listAllClassWithNoCourse(int departmentId){
        List<Class> list = classService.listClassByDepartmentId(departmentId);
        return ResultUtil.success(list);
    }

    @ApiOperation(value="获取班级列表(包括课程信息)")
    @GetMapping("/searchClass")
    public ResultUtil searchClass(int startPage, int pageSize, String str, String grade, String departmentId ){
        IPage<Class> list = classService.searchClass(startPage, pageSize, str, grade, departmentId);
        Map<String,Object> map = new HashMap<>();
        map.put("list",list.getRecords());
        map.put("total",list.getTotal());
        return ResultUtil.success(map);
    }

    @GetMapping("/listAllGrade")
    public ResultUtil listAllGrade(){
        List<String> list = classService.listGrade();
        return ResultUtil.success(list);
    }

}
