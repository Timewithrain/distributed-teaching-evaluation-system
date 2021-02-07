package com.watermelon.controller;

import com.watermelon.api.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String PROVIDER_URL = "http://distribute-course-manage-provider/department";

    @PostMapping("/addDepartment")
    public Map<String, String> addDepartment(@RequestBody(required=false) Department department){
        ResponseEntity<Map> entity = restTemplate.postForEntity(PROVIDER_URL+"/addDepartment",department,Map.class);
        return entity.getBody();
    }

    @PutMapping("/updateDepartment")
    public Map<String, String> updateDepartment(@RequestBody(required=false) Department department){
        restTemplate.put(PROVIDER_URL+"/updateDepartment",department,Map.class);
        Map<String,String> map = new HashMap<>();
        map.put("status","200");
        map.put("massage","update department succeed");
        return map;
    }

    @DeleteMapping("/deleteDepartment/{id}")
    public Map<String, String> deleteDepartment(@RequestParam(value="id",required=false) int id){
        String str = PROVIDER_URL+"/deleteDepartment/{id}?id="+id;
        restTemplate.delete(str,id);
        Map<String,String> map = new HashMap<>();
        map.put("status","200");
        map.put("massage","delete department succeed");
        return map;
    }

    @GetMapping("/listDepartment")
    public List<Department> listDepartment(int startPage, int pageSize){
        String params = "?pageSize=" + pageSize + "&startPage=" + startPage;
        return restTemplate.getForObject(PROVIDER_URL+"/listDepartment"+params,List.class);
    }
}
