package com.watermelon.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.watermelon.api.entity.Department;
import com.watermelon.api.service.DepartmentService;
import com.watermelon.api.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/addDepartment")
    public ResultUtil addDepartment(@RequestBody(required=false) Department department){
        departmentService.addDepartment(department);
        return ResultUtil.success(null,"add department succeed");
    }

    @PutMapping("/updateDepartment")
    public ResultUtil updateDepartment(@RequestBody(required=false) Department department){
        departmentService.updateDepartment(department);
        return ResultUtil.success(null,"update department succeed");
    }

    @DeleteMapping("/deleteDepartment")
    public ResultUtil deleteDepartment(@RequestParam(value="id",required=false) int id){
        departmentService.deleteDepartment(id);
        return ResultUtil.success(null,"delete department succeed");
    }

    @GetMapping("/listDepartment")
    public ResultUtil listDepartment(int startPage, int pageSize){
        IPage<Department> page = departmentService.listDepartment(startPage, pageSize);
        Map<String,Object> map = new HashMap<>();
        map.put("list",page.getRecords());
        map.put("total",page.getTotal());
        return ResultUtil.success(map);
    }

    @GetMapping("/listAllDepartment")
    public ResultUtil listAllDepartment(){
        List<Department> list = departmentService.listAllDepartment();
        return ResultUtil.success(list);
    }

    @GetMapping("/searchDepartment")
    public ResultUtil searchDepartment(int startPage, int pageSize, String str){
        IPage<Department> page = departmentService.searchDepartment(startPage, pageSize, str);
        Map<String,Object> map = new HashMap<>();
        map.put("list",page.getRecords());
        map.put("total",page.getTotal());
        return ResultUtil.success(map);
    }
}
