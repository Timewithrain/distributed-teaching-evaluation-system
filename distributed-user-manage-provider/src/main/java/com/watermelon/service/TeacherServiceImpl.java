package com.watermelon.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.watermelon.api.entity.*;
import com.watermelon.api.service.RoleService;
import com.watermelon.api.service.TeacherService;
import com.watermelon.api.service.UserService;
import com.watermelon.mapper.TeacherMapper;
import com.watermelon.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private UserService userService;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private RoleService roleService;

    @Override
    public Teacher getTeacherById(int id) {
        Teacher teacher = teacherMapper.getTeacherById(id);
        User u = userService.getUserById(id);
        if (u!=null){
            teacher.addUserInfo(u);
        }
        return teacher;
    }

    @Override
    public void addTeacher(Teacher teacher) {
        teacher.setId(teacherMapper.getMaxUserId()+1);
        userService.addUser(toUser(teacher));
        teacherMapper.addTeacher(teacher);
    }

    @Override
    public void updateTeacher(Teacher teacher) {
        userService.updateUser(toUser(teacher));
        teacherMapper.updateTeacher(teacher);
    }

    @Override
    public void deleteTeacher(int id) {
        teacherMapper.deleteTeacher(id);
        userService.deleteUser(id);
    }

    @Override
    public void deleteTeacherByDepartmentId(int departmentId) {
        List<Teacher> list = teacherMapper.listTeacherByDepartmentId(departmentId);
        for (Teacher t : list) {
            deleteTeacher(t.getId());
        }
    }

    @Override
    public IPage<Teacher> listTeacher(int startPage, int pageSize) {
        IPage<Teacher> page = new Page<>(startPage,pageSize);
        IPage<Teacher> listPage = teacherMapper.listTeacher(page);
        List<Teacher> list = addUserInformation(listPage.getRecords());
        listPage.setRecords(list);
        return listPage;
    }

    @Override
    public List<Teacher> listAllTeacher(){
        List<Teacher> list = teacherMapper.listTeacher();
        return addUserInformation(list);
    }

    @Override
    public List<Teacher> listTeacherByDepartmentId(int departmentId){
        return teacherMapper.listTeacherByDepartmentId(departmentId);
    }

    @Override
    public IPage<Course> listCourseByTeacherId(int startPage, int pageSize, int teacherId) {
        Page<Course> page = new Page<>(startPage,pageSize);
        return teacherMapper.listCourseByTeacherId(page,teacherId);
    }

    @Override
    public List<Teacher> searchAllTeacher(String str, String departmentId) {
        List<Teacher> list = teacherMapper.searchTeacher(str, departmentId);
        return addUserInformation(list);
    }

    @Override
    public IPage<Teacher> searchTeacher(int startPage, int pageSize, String str, String departmentId) {
        IPage<Teacher> page = new Page<>(startPage, pageSize);
        IPage<Teacher> list = teacherMapper.searchTeacher(page, str, departmentId);
        list.setRecords(addUserInformation(list.getRecords()));
        return list;
    }

    /**
     * 将Teacher转换为User便于存储和管理
     * @return user User
     */
    public User toUser(Teacher teacher){
        User user = new User();
        user.setId(teacher.getId());
        user.setName(teacher.getName());
        user.setPassword(teacher.getPassword());
        user.setRoleId(teacher.getRole().getId());
        user.setRole(teacher.getRole());
        user.setIdNumber(teacher.getIdNumber());
        return user;
    }

    /**
     * 私有方法，为教师用户添加个人用户信息
     * @param list
     * @return
     */
    private List<Teacher> addUserInformation(List<Teacher> list) {
        for (Teacher t : list) {
            User u = userService.getUserById(t.getId());
            if (u!=null){
                t.addUserInfo(u);
                Role r = roleService.getRoleById(t.getRoleId());
                if (r!=null){
                    t.setRole(r);
                }
            }
        }
        return list;
    }
}
