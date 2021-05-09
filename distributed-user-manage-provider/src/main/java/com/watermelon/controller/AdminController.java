package com.watermelon.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.watermelon.api.entity.*;
import com.watermelon.api.service.*;
import com.watermelon.api.util.ResultUtil;
import com.watermelon.api.util.StatusCode;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SupervisorService supervisorService;

    @Deprecated
    @PostMapping("/addUser")
    public ResultUtil addUser(@RequestBody(required=false) User user) {
        userService.addUser(user);
        return ResultUtil.success(null, "添加用户成功");
    }

    @PutMapping("/updateUser")
    public ResultUtil updateUser(@RequestBody(required=false) User user){
        userService.updateUser(user);
        return ResultUtil.success();
    }

    @DeleteMapping("/deleteUser")
    public ResultUtil deleteUser(@RequestParam(value="id",required=false) int id){
        userService.deleteUser(id);
        return ResultUtil.success(null, "删除用户成功");
    }

    @ResponseBody
    @GetMapping("/getUserById")
    public ResultUtil getUserById(@RequestParam(value = "id") int id) {
        User user = userService.getUserById(id);
        if (user!=null) {
            return ResultUtil.success(user);
        }else {
            return ResultUtil.error(StatusCode.BAD_REQUEST);
        }
    }

    @GetMapping("/listUser")
    public ResultUtil listUser(int startPage, int pageSize) {
        List<User> list = userService.listUser(startPage, pageSize);
        Map<String,Object> map = new HashMap<>();
        map.put("list",list);
        map.put("total",userService.getUserNumber());
        return ResultUtil.success(map);
    }

    @GetMapping("/searchUser")
    public ResultUtil listUser(int startPage, int pageSize, String query) {
        IPage<User> list = userService.searchUser(startPage, pageSize, query);
        Map<String,Object> map = new HashMap<>();
        map.put("list",list.getRecords());
        map.put("total",list.getTotal());
        return ResultUtil.success(map);
    }

    @PostMapping("/addRole")
    public ResultUtil addRole(@RequestBody(required=false) Role role){
        roleService.addRole(role);
        return ResultUtil.success(null,"添加角色成功");
    }

    @PutMapping("/updateRole")
    public ResultUtil updateRole(@RequestBody(required=false) Role role){
        roleService.updateRole(role);
        return ResultUtil.success(null,"修改角色成功");
    }

    @PostMapping("/addRolePermission")
    public ResultUtil addRolePermission(@RequestBody(required=false) Map<String,Integer> params){
        int roleId = params.get("roleId");
        int permsId = params.get("permsId");
        roleService.addRolePermission(roleId, permsId);
        return ResultUtil.success(null,"成功为角色添加权限");
    }

    @PutMapping("/updateRolePerms")
    public ResultUtil updateRolePermissions(@RequestParam(value="roleId",required=false) int roleId,
                                            @RequestParam(value="perms",required=false) String perms){
        roleService.updateRolePermissions(roleId,perms);
        return ResultUtil.success();
    }

    @DeleteMapping("/deleteRolePermission")
    public ResultUtil deleteRolePermission(@RequestParam(value="roleId",required=false) int roleId,
                                                    @RequestParam(value="permsId",required=false) int permsId){
        roleService.deleteRolePermission(roleId, permsId);
        return ResultUtil.success(null,"成功删除角色权限");
    }

    @DeleteMapping("/deleteRole")
    public ResultUtil deleteRole(@RequestParam(value="id",required=false) int id){
        roleService.deleteRole(id);
        return ResultUtil.success(null,"删除角色成功");
    }

    @ResponseBody
    @GetMapping("/listRole")
    public ResultUtil listRole(int startPage, int pageSize){
        IPage<Role> list = roleService.listRole(startPage, pageSize);
        Map<String,Object> map = new HashMap<>();
        map.put("list",list.getRecords());
        map.put("total",list.getTotal());
        return ResultUtil.success(map);
    }

    @ResponseBody
    @GetMapping("/listAllRole")
    public ResultUtil listAllRole(){
        List<Role> list = roleService.listRole();
        return ResultUtil.success(list);
    }

    @PostMapping("/addPerms")
    public ResultUtil addPerms(@RequestBody(required=false) Permission permission){
        permissionService.addPermission(permission);
        return ResultUtil.success(null,"添加权限成功");
    }

    @PutMapping("/updatePerms")
    public ResultUtil updatePerms(@RequestBody(required=false) Permission permission) {
        permissionService.updatePermission(permission);
        return ResultUtil.success(null,"修改权限成功");
    }

    @ResponseBody
    @DeleteMapping("/deletePerms")
    public ResultUtil deletePerms(@RequestParam(value="id",required=false) int id){
        permissionService.deletePermission(id);
        return ResultUtil.success(null,"删除权限成功");
    }

    @ResponseBody
    @GetMapping("/listAllPerms")
    public ResultUtil listAllPermission(){
        List<Permission> list = permissionService.listPermission();
        return ResultUtil.success(list);
    }

    @ResponseBody
    @GetMapping("/listPerms")
    public ResultUtil listPermission(int startPage, int pageSize){
        List<Permission> list = permissionService.listPermission(startPage, pageSize);
        int total = permissionService.getPermissionNumber();
        Map<String,Object> map = new HashMap<>();
        map.put("list",list);
        map.put("total",total);
        return ResultUtil.success(map);
    }

    @PostMapping("/addSupervisor")
    public ResultUtil addSupervisor(@RequestBody(required=false) Supervisor supervisor){
        supervisorService.addSupervisor(supervisor);
        return ResultUtil.success(null,"添加督导成功");
    }

    @PutMapping("/updateSupervisor")
    public ResultUtil updateSupervisor(@RequestBody(required=false) Supervisor supervisor) {
        supervisorService.updateSupervisor(supervisor);
        return ResultUtil.success(null,"修改督导成功");
    }

    @DeleteMapping("/deleteSupervisor")
    public ResultUtil deleteSupervisor(@RequestParam(value="id",required=false) int id){
        supervisorService.deleteSupervisor(id);
        return ResultUtil.success(null,"删除督导成功");
    }

    @ResponseBody
    @GetMapping("/searchSupervisor")
    public ResultUtil listSupervisor(int startPage, int pageSize, String str){
        IPage<Supervisor> list = supervisorService.searchSupervisor(startPage, pageSize, str);
        Map<String,Object> map = new HashMap<>();
        map.put("list",list.getRecords());
        map.put("total",list.getTotal());
        return ResultUtil.success(map);
    }

    @PostMapping("/addTeacher")
    public ResultUtil addTeacher(@RequestBody(required=false) Teacher teacher){
        teacherService.addTeacher(teacher);
        return ResultUtil.success(null,"添加教师成功");
    }

    @PutMapping("/updateTeacher")
    public ResultUtil updateTeacher(@RequestBody(required=false) Teacher teacher) {
        teacherService.updateTeacher(teacher);
        return ResultUtil.success(null,"修改教师成功");
    }

    @DeleteMapping("/deleteTeacher")
    public ResultUtil deleteTeacher(@RequestParam(value="id",required=false) int id){
        teacherService.deleteTeacher(id);
        return ResultUtil.success(null,"删除教师成功");
    }

    /**
     * 根据学院id删除对应学院的全部教师信息，在删除学院时由distributed-course-manage模块调用
     * @param id
     * @return
     */
    @DeleteMapping("/deleteTeacherByDepartmentId")
    public ResultUtil deleteTeacherByDepartmentId(@RequestParam(value="id",required=false) int id){
        teacherService.deleteTeacherByDepartmentId(id);
        return ResultUtil.success(null,"成功删除"+id+"号学院的教师");
    }

    @GetMapping("/listTeacher")
    public ResultUtil listTeacher(int startPage, int pageSize){
        IPage<Teacher> list =  teacherService.listTeacher(startPage, pageSize);
        Map<String,Object> map = new HashMap<>();
        map.put("list",list.getRecords());
        map.put("total",list.getTotal());
        return ResultUtil.success(map);
    }

    @GetMapping("/listAllTeacher")
    public ResultUtil listAllTeacher(){
        List<Teacher> list =  teacherService.listAllTeacher();
        return ResultUtil.success(list);
    }

    @GetMapping("/searchAllTeacher")
    public ResultUtil searchTeacher(String str){
        List<Teacher> list = teacherService.searchAllTeacher(str,"");
        return ResultUtil.success(list);
    }

    @GetMapping("/searchTeacher")
    public ResultUtil searchTeacher(int startPage, int pageSize,String str,String departmentId){
        IPage<Teacher> page = teacherService.searchTeacher(startPage, pageSize, str, departmentId);
        Map<String,Object> map = new HashMap<>();
        map.put("list",page.getRecords());
        map.put("total",page.getTotal());
        return ResultUtil.success(map);
    }

    @PostMapping("/addStudent")
    public ResultUtil addStudent(@RequestBody(required=false) Student student){
        studentService.addStudent(student);
        return ResultUtil.success(null,"添加学生成功");
    }

    @PutMapping("/updateStudent")
    public ResultUtil updateStudent(@RequestBody(required=false) Student student) {
        studentService.updateStudent(student);
        return ResultUtil.success(null,"修改学生成功");
    }

    @DeleteMapping("/deleteStudent")
    public ResultUtil deleteStudent(@RequestParam(value="id",required=false) int id){
        studentService.deleteStudent(id);
        return ResultUtil.success(null,"删除学生成功");
    }

    @DeleteMapping("/deleteStudentByClassId")
    public ResultUtil deleteStudentByClassId(@RequestParam(value="classId",required=false) int classId){
        studentService.deleteStudentByClassId(classId);
        return ResultUtil.success(null,"成功删除"+classId+"号班级的学生");
    }

    @GetMapping("/listStudentWithNoCourse")
    public ResultUtil listStudentWithNoCourse(int startPage, int pageSize){
        IPage<Student> list = studentService.listStudentWithNoCourse(startPage, pageSize);
        return ResultUtil.success(list);
    }

    @GetMapping("/listStudent")
    public ResultUtil listStudent(int startPage, int pageSize){
        IPage<Student> list = studentService.listStudent(startPage, pageSize);
        return ResultUtil.success(list);
    }

    @GetMapping("/listStudentByClassId")
    public ResultUtil listStudentByClassId(int startPage, int pageSize,int classId){
        IPage<Student> list = studentService.listStudentByClassId(startPage, pageSize, classId);
        return ResultUtil.success(list);
    }

    @GetMapping("/searchStudent")
    public ResultUtil searchStudent(@ApiParam(value="起始页",example="1") @RequestParam(value="startPage",required=false) int startPage,
                                    @ApiParam(value="页数",example="5") @RequestParam(value="pageSize",required=false) int pageSize,
                                    @ApiParam(value="查询关键字",example="张阳") @RequestParam(value="str",required=false) String str,
                                    @ApiParam(value="查询班级号",example="1") @RequestParam(value="classId",required=false) String classId,
                                    @ApiParam(value="查询学院号",example="1") @RequestParam(value="departmentId",required=false) String departmentId){
        IPage<Student> list = studentService.searchStudent(startPage, pageSize,str,classId,departmentId);
        Map<String,Object> map = new HashMap<>();
        map.put("list",list.getRecords());
        map.put("total",list.getTotal());
        return ResultUtil.success(map);
    }

    @PostMapping("/addAdmin")
    public ResultUtil addAdmin(@RequestBody(required=false) Admin admin){
        adminService.addAdmin(admin);
        return ResultUtil.success(null,"添加管理员成功");
    }

    @PutMapping("/updateAdmin")
    public ResultUtil updateAdmin(@RequestBody(required=false) Admin admin){
        adminService.updateAdmin(admin);
        return ResultUtil.success(null,"修改管理员成功");
    }

    @DeleteMapping("/deleteAdmin")
    public ResultUtil deleteAdmin(@RequestParam(value="id",required=false) int id){
        adminService.deleteAdmin(id);
        return ResultUtil.success(null,"删除管理员成功");
    }

}
