package com.watermelon.controller;

import com.watermelon.api.entity.Class;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/class")
public class ClassController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String PROVIDER_URL = "http://distribute-course-manage-provider/class";

    @ApiOperation(value="添加班级(包括Department,不包括Course信息)")
    @PostMapping("/addClass")
    public Map<String, String> addClass(@RequestBody(required=false)Class aClass){
        ResponseEntity<Map> entity = restTemplate.postForEntity(PROVIDER_URL+"/addClass",aClass,Map.class);
        return entity.getBody();
    }

    @ApiOperation(value="更新班级信息(包括Department,不包括Course信息)")
    @PutMapping("/updateClass")
    public Map<String, String> updateClass(@RequestBody(required=false) Class aClass){
        restTemplate.put(PROVIDER_URL+"/updateClass",aClass,Map.class);
        Map<String,String> map = new HashMap<>();
        map.put("status","200");
        map.put("massage","update class succeed");
        return map;
    }

    @ApiOperation(value="删除班级(级联删除)")
    @DeleteMapping("/deleteClass/{id}")
    public Map<String, String> deleteClass(@RequestParam(value = "id",required=false) int id){
        restTemplate.delete(PROVIDER_URL+"/deleteClass/{id}?id="+id,id);
        Map<String,String> map = new HashMap<>();
        map.put("status","200");
        map.put("massage","delete class succeed");
        return map;
    }

    @ApiOperation(value="为班级添加课程")
    @GetMapping("/addClassCourse")
    public Map<String, String> addClassCourse(
            @ApiParam(value="班级号",example="1") @RequestParam(value="classId",required=false) int classId,
            @ApiParam(value="课程号",example="1") @RequestParam(value="courseId",required=false) int courseId,
            @ApiParam(value="教师号",example="1") @RequestParam(value="teacherId",required=false) int teacherId){
        String params = "?classId=" + classId + "&courseId=" + courseId + "&teacherId=" + teacherId;
        return restTemplate.getForObject(PROVIDER_URL+"/addClassCourse"+params,Map.class);
    }

    @ApiOperation(value="删除班级的对应课程")
    @DeleteMapping("/deleteClassCourse")
    public Map<String, String> deleteClassCourse(
            @ApiParam(value="班级号",example="1") @RequestParam(value="classId",required=false) int classId,
            @ApiParam(value="课程号",example="1") @RequestParam(value="courseId",required=false)int courseId){
        String params = "?classId=" + classId + "&courseId=" + courseId;
        restTemplate.delete(PROVIDER_URL+"/deleteClassCourse/"+params);
        Map<String,String> map = new HashMap<>();
        map.put("status","200");
        map.put("massage","delete the course of the class successfully");
        return map;
    }

    @ApiOperation(value="获取班级列表(包括课程信息)")
    @GetMapping("/listClass")
    public List<Class> listClass(int startPage, int pageSize){
        String params = "?pageSize=" + pageSize + "&startPage=" + startPage;
        Class[] classes = restTemplate.getForObject(PROVIDER_URL+"/listClass"+params,Class[].class);
        List list = Arrays.asList(classes);
        return list;
    }

    @ApiOperation(value="获取班级列表(不包括课程信息)")
    @GetMapping("/listClassWithNoCourse")
    public List<Class> listClassWithNoCourse(int startPage, int pageSize){
        String params = "?pageSize=" + pageSize + "&startPage=" + startPage;
        return restTemplate.getForObject(PROVIDER_URL+"/listClassWithNoCourse"+params,List.class);
    }


}
