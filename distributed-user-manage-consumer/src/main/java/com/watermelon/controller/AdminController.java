package com.watermelon.controller;

import com.watermelon.api.entity.*;
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
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String PROVIDER_URL = "http://distribute-user-manage-provider/admin";

    @PostMapping("/addUser")
    public Map<String,String> addUser(@RequestBody(required=false) User user) {
        ResponseEntity<Map> entity = restTemplate.postForEntity(PROVIDER_URL+"/addUser",user,Map.class);
        return entity.getBody();
    }

    @PutMapping("/updateUser")
    public Map<String, String> updateUser(@RequestBody(required=false) User user){
        restTemplate.put(PROVIDER_URL+"/updateUser",user,Map.class);
        Map<String,String> map = new HashMap<>();
        map.put("status","200");
        map.put("message","修改用户成功");
        return map;
    }

    @DeleteMapping("/deleteUser")
    public Map<String, String> deleteUser(@RequestParam(value="id",required=false) int id){
        restTemplate.delete(PROVIDER_URL+"/deleteUser/{id}?id="+id,id);
        Map<String,String> map = new HashMap<>();
        map.put("status","200");
        map.put("message","删除用户成功");
        return map;
    }

    @GetMapping("/listUser")
    public List<User> listUser(int startPage, int pageSize) {
        String params = "?pageSize=" + pageSize + "&startPage=" + startPage;
        User[] users = restTemplate.getForObject(PROVIDER_URL+"/listUser"+params,User[].class);
        return Arrays.asList(users);
    }

    @PostMapping("/addRole")
    public Map<String, String> addRole(@RequestBody(required=false) Role role){
        ResponseEntity<Map> entity = restTemplate.postForEntity(PROVIDER_URL+"/addRole",role,Map.class);
        return entity.getBody();
    }

    @PutMapping("/updateRole")
    public Map<String, String> updateRole(@RequestBody(required=false) Role role){
        restTemplate.put(PROVIDER_URL+"/updateRole",role,Map.class);
        Map<String,String> map = new HashMap<>();
        map.put("status","200");
        map.put("message","修改角色成功");
        return map;
    }

    @PostMapping("/addRolePermission")
    public Map<String, String> addRolePermission(@RequestParam(value="roleId",required=false) int roleId,
                                                 @RequestParam(value="permsId",required=false) int permsId){
        Map<String,Integer> params = new HashMap<>();
        params.put("roleId",roleId);
        params.put("permsId",permsId);
        ResponseEntity<Map> entity = restTemplate.postForEntity(PROVIDER_URL+"/addRolePermission",params,Map.class);
        return entity.getBody();
    }

    @DeleteMapping("/deleteRolePermission")
    public Map<String, String> deleteRolePermission(@RequestParam(value="roleId",required=false) int roleId,
                                                    @RequestParam(value="permsId",required=false) int permsId){
        String paramURL = "?roleId={roleId}&permsId={permsId}";
        Map<String,Integer> params = new HashMap();
        params.put("roleId",roleId);
        params.put("permsId",permsId);
        restTemplate.delete(PROVIDER_URL+"/deleteRolePermission"+paramURL,params);
        Map<String,String> map = new HashMap<>();
        map.put("status","200");
        map.put("message","成功删除角色权限");
        return map;
    }

    @DeleteMapping("/deleteRole")
    public Map<String, String> deleteRole(@RequestParam(value="id",required=false) int id){
        restTemplate.delete(PROVIDER_URL+"/deleteRole/{id}?id="+id,id);
        Map<String,String> map = new HashMap<>();
        map.put("status","200");
        map.put("message","删除角色成功");
        return map;
    }

    @ResponseBody
    @GetMapping("/listRole")
    public List<Role> listRole(int startPage, int pageSize){
        String params = "?pageSize=" + pageSize + "&startPage=" + startPage;
        Role[] roles = restTemplate.getForObject(PROVIDER_URL+"/listRole"+params,Role[].class);
        return Arrays.asList(roles);
    }

    @PostMapping("/addPerms")
    public Map<String, String> addPerms(@RequestBody(required=false) Permission permission){
        ResponseEntity<Map> entity = restTemplate.postForEntity(PROVIDER_URL+"/addPerms",permission,Map.class);
        return entity.getBody();
    }

    @PutMapping("/updatePerms")
    public Map<String, String> updatePerms(@RequestBody(required=false) Permission permission) {
        restTemplate.put(PROVIDER_URL+"/updatePerms",permission,Map.class);
        Map<String,String> map = new HashMap<>();
        map.put("status","200");
        map.put("message","修改权限成功");
        return map;
    }

    @DeleteMapping("/deletePerms")
    public Map<String, String> deletePerms(@RequestParam(value="id",required=false) int id){
        restTemplate.delete(PROVIDER_URL+"/deletePerms/{id}?id="+id,id);
        Map<String,String> map = new HashMap<>();
        map.put("status","200");
        map.put("message","删除权限成功");
        return map;
    }

    @ResponseBody
    @GetMapping("/listPerms")
    public List<Permission> listPermission(int startPage, int pageSize){
        String params = "?pageSize=" + pageSize + "&startPage=" + startPage;
        return restTemplate.getForObject(PROVIDER_URL+"/listPerms"+params,List.class);
    }

    @PostMapping("/addSupervisor")
    public Map<String, String> addSupervisor(@RequestBody(required=false) Supervisor supervisor){
        ResponseEntity<Map> entity = restTemplate.postForEntity(PROVIDER_URL+"/addSupervisor",supervisor,Map.class);
        return entity.getBody();
    }

    @PutMapping("/updateSupervisor")
    public Map<String, String> updateSupervisor(@RequestBody(required=false) Supervisor supervisor) {
        restTemplate.put(PROVIDER_URL+"/updateSupervisor",supervisor,Map.class);
        Map<String,String> map = new HashMap<>();
        map.put("status","200");
        map.put("message","修改督导成功");
        return map;
    }

    @DeleteMapping("/deleteSupervisor")
    public Map<String, String> deleteSupervisor(@RequestParam(value="id",required=false) int id){
        restTemplate.delete(PROVIDER_URL+"/deleteSupervisor/{id}?id="+id,id);
        Map<String,String> map = new HashMap<>();
        map.put("status","200");
        map.put("message","删除督导成功");
        return map;
    }

    @ResponseBody
    @GetMapping("/listSupervisor")
    public List<Supervisor> listSupervisor(int startPage, int pageSize){
        String params = "?pageSize=" + pageSize + "&startPage=" + startPage;
        return restTemplate.getForObject(PROVIDER_URL+"/listSupervisor"+params,List.class);
    }

    @PostMapping("/addTeacher")
    public Map<String, String> addTeacher(@RequestBody(required=false) Teacher teacher){
        ResponseEntity<Map> entity = restTemplate.postForEntity(PROVIDER_URL+"/addTeacher",teacher,Map.class);
        return entity.getBody();
    }

    @PutMapping("/updateTeacher")
    public Map<String, String> updateTeacher(@RequestBody(required=false) Teacher teacher) {
        restTemplate.put(PROVIDER_URL+"/updateTeacher",teacher,Map.class);
        Map<String,String> map = new HashMap<>();
        map.put("status","200");
        map.put("message","修改教师成功");
        return map;
    }

    @DeleteMapping("/deleteTeacher")
    public Map<String, String> deleteTeacher(@RequestParam(value="id",required=false) int id){
        restTemplate.delete(PROVIDER_URL+"/deleteTeacher/{id}?id="+id,id);
        Map<String,String> map = new HashMap<>();
        map.put("status","200");
        map.put("message","删除教师成功");
        return map;
    }

    @ResponseBody
    @GetMapping("/listTeacher")
    public List<Teacher> listTeacher(int startPage, int pageSize){
        String params = "?pageSize=" + pageSize + "&startPage=" + startPage;
        return restTemplate.getForObject(PROVIDER_URL+"/listTeacher"+params,List.class);
    }

    @PostMapping("/addStudent")
    public Map<String, String> addStudent(@RequestBody(required=false) Student student){
        ResponseEntity<Map> entity = restTemplate.postForEntity(PROVIDER_URL+"/addStudent",student,Map.class);
        return entity.getBody();
    }

    @PutMapping("/updateStudent")
    public Map<String, String> updateStudent(@RequestBody(required=false) Student student) {
        restTemplate.put(PROVIDER_URL+"/updateStudent",student,Map.class);
        Map<String,String> map = new HashMap<>();
        map.put("status","200");
        map.put("message","修改学生成功");
        return map;
    }

    @DeleteMapping("/deleteStudent")
    public Map<String, String> deleteStudent(@RequestParam(value="id",required=false) int id){
        restTemplate.delete(PROVIDER_URL+"/deleteStudent/{id}?id="+id,id);
        Map<String,String> map = new HashMap<>();
        map.put("status","200");
        map.put("message","删除学生成功");
        return map;
    }

    @GetMapping("/listStudentWithNoCourse")
    public List<Student> listStudentWithNoCourse(int startPage, int pageSize){
        String params = "?pageSize=" + pageSize + "&startPage=" + startPage;
        return restTemplate.getForObject(PROVIDER_URL+"/listStudentWithNoCourse"+params,List.class);
    }

    @GetMapping("/listStudent")
    public List<Student> listStudent(int startPage, int pageSize){
        String params = "?pageSize=" + pageSize + "&startPage=" + startPage;
        return restTemplate.getForObject(PROVIDER_URL+"/listStudent"+params,List.class);
    }

    @GetMapping("/listStudentByClassId")
    public List<Student> listStudentByClassId(int startPage, int pageSize,int classId){
        String params = "?pageSize=" + pageSize + "&startPage=" + startPage + "&classId=" + classId;
        return restTemplate.getForObject(PROVIDER_URL+"/listStudentByClassId"+params,List.class);
    }

    @GetMapping("/searchStudent")
    public List<Student> searchStudent(@ApiParam(value="起始页",example="1") @RequestParam(value="startPage",required=false) int startPage,
                                       @ApiParam(value="页数",example="5") @RequestParam(value="pageSize",required=false) int pageSize,
                                       @ApiParam(value="查询关键字",example="张阳") @RequestParam(value="str",required=false) String str){
        String params = "?pageSize=" + pageSize + "&startPage=" + startPage + "&str=" + str;
        return restTemplate.getForObject(PROVIDER_URL+"/searchStudent"+params,List.class);
    }

    @PostMapping("/addAdmin")
    public Map<String,String> addAdmin(@RequestBody(required=false) Admin admin){
        ResponseEntity<Map> entity = restTemplate.postForEntity(PROVIDER_URL+"/addStudent",admin,Map.class);
        return entity.getBody();
    }

    @PutMapping("/updateAdmin")
    public Map<String,String> updateAdmin(@RequestBody(required=false) Admin admin){
        restTemplate.put(PROVIDER_URL+"/updateAdmin",admin,Map.class);
        Map<String,String> map = new HashMap<>();
        map.put("status","200");
        map.put("message","修改管理员成功");
        return map;
    }

    @DeleteMapping("/deleteAdmin")
    public Map<String,String> deleteAdmin(@RequestParam(value="id",required=false) int id){
        restTemplate.delete(PROVIDER_URL+"/deleteAdmin/{id}?id="+id,id);
        Map<String,String> map = new HashMap<>();
        map.put("status","200");
        map.put("message","删除管理员成功");
        return map;
    }

}
