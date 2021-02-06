package com.watermelon.controller;

import com.watermelon.api.entity.Course;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courseManage")
public class CourseManageConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String PROVIDER_URL = "http://distribute-course-manage-provider/courseManage";

    @PostMapping("/addCourse")
    public Map<String, String> addCourse(@RequestBody(required=false) Course course){
        ResponseEntity<Map> entity = restTemplate.postForEntity(PROVIDER_URL+"/addCourse",course,Map.class);
        return entity.getBody();
    }

    @PutMapping("/updateCourse")
    public Map<String, String> updateCourse(@RequestBody(required=false) Course course){
        restTemplate.put(PROVIDER_URL+"/updateCourse",course,Map.class);
        Map map = new HashMap<String,String>();
        map.put("status","200");
        map.put("message","update succeed");
        return map;
    }

    @DeleteMapping("/deleteCourse/{id}")
    public void deleteCourse(@RequestParam(value="id",required=false) int id){
        String str = PROVIDER_URL+"/deleteCourse/{id}?id="+id;
        restTemplate.delete(str,id);
    }

    @GetMapping("/listCourseWithNoTeacher/")
    public List listCourseWithNoTeacher(int pageSize, int startPage){
        String params = "?pageSize=" + pageSize + "&startPage=" + startPage;
        return restTemplate.getForObject(PROVIDER_URL+"/listCourseWithNoTeacher"+params,List.class);
    }

    @GetMapping("/listCourse")
    public List<Course> listCourse(int startPage, int pageSize){
        String params = "?pageSize=" + pageSize + "&startPage=" + startPage;
        return restTemplate.getForObject(PROVIDER_URL+"/listCourse"+params,List.class);
    }

    @GetMapping("/listCourseByClassId")
    public List<Course> listCourseByClassId(int startPage, int pageSize, int classId){
        String params =  "?classId=" + classId + "&pageSize=" + pageSize + "&startPage=" + startPage;
        return restTemplate.getForObject(PROVIDER_URL+"/listCourseByClassId"+params,List.class);
    }

    @GetMapping("/listCourseByDepartmentId")
    public List<Course> listCourseByDepartmentId(@ApiParam(value="起始页",example="1") @RequestParam(value="startPage",required=false) int startPage,
                                                 @ApiParam(value="页数",example="5") @RequestParam(value="pageSize",required=false) int pageSize,
                                                 @ApiParam(value="学院号",example="1") @RequestParam(value="departmentId",required=false) int departmentId){
//        String url = PROVIDER_URL+"/listCourseByDepartmentId?departmentId={departmentId}&pageSize={pageSize}&startPage={startPage}";
//        System.out.println(url);
//        Map<String,String> params = new HashMap<>();
//        params.put("departmentId",departmentId+"");
//        params.put("pageSize",pageSize+"");
//        params.put("startPage",startPage+"");
//        return restTemplate.getForObject(url,List.class,params);
        String params = "?departmentId=" + departmentId + "&pageSize=" + pageSize + "&startPage=" + startPage;
        return restTemplate.getForObject(PROVIDER_URL+"/listCourseByDepartmentId"+params,List.class);
    }

    @GetMapping("/searchCourse")
    public List<Course> searchCourse(@ApiParam(value="起始页",example="1") @RequestParam(value="startPage",required=false) int startPage,
                                     @ApiParam(value="页数",example="5") @RequestParam(value="pageSize",required=false) int pageSize,
                                     @ApiParam(value="查询关键字",example="计算机") @RequestParam(value="str",required=false) String str){
        String params = "?pageSize=" + pageSize + "&startPage=" + startPage + "&str=" + str;
        return restTemplate.getForObject(PROVIDER_URL+"/searchCourse"+params,List.class);
    }

}
