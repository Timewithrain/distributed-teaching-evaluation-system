package com.watermelon.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.watermelon.api.entity.*;
import com.watermelon.api.service.RoleService;
import com.watermelon.api.service.SupervisorService;
import com.watermelon.mapper.SupervisorMapper;
import com.watermelon.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupervisorServiceImpl implements SupervisorService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SupervisorMapper supervisorMapper;

    @Override
    public List<Supervisor> getAllSupervisor() {
        return supervisorMapper.getAllSupervisor();
    }

    @Override
    public int addSupervisorCourse(int supervisorId, int courseId, int teacherId) {
        return supervisorMapper.addSupervisorCourse(supervisorId,courseId,teacherId);
    }

    @Override
    public int deleteSupervisorCourse(int supervisorId, int courseId, int teacherId) {
        return supervisorMapper.deleteSupervisorCourse(supervisorId,courseId,teacherId);
    }


    @Override
    public Supervisor getSupervisorById(int id) {
        Supervisor supervisor = supervisorMapper.getSupervisorById(id);
        User u = userMapper.getUserById(id);
        if (u!=null){
            supervisor.addUserInfo(u);
            Role r = roleService.getRoleById(supervisor.getRoleId());
            if (r!=null){
                supervisor.setRole(r);
            }
        }
        return supervisor;
    }

    @Override
    public void addSupervisor(Supervisor supervisor) {
        supervisor.setId(supervisorMapper.getMaxSupervisorId()+1);
        userMapper.addUser(toUser(supervisor));
        supervisorMapper.addSupervisor(supervisor);
    }

    @Override
    public void updateSupervisor(Supervisor supervisor) {
        Supervisor s = getSupervisorById(supervisor.getId());
        if (s!=null){
            userMapper.updateUser(toUser(supervisor));
            supervisorMapper.updateSupervisor(supervisor);
        }
    }

    @Override
    public void deleteSupervisor(int id) {
        supervisorMapper.deleteSupervisor(id);
        userMapper.deleteUser(id);
    }

    @Override
    public List<Supervisor> listSupervisor(int startPage, int pageSize) {
        Page<Supervisor> page = new Page<>(startPage,pageSize);
        List<Supervisor> list = supervisorMapper.listSupervisor(page);
        for (Supervisor s : list){
            User u = userMapper.getUserById(s.getId());
            if (u!=null){
                s.addUserInfo(u);
                Role r = roleService.getRoleById(s.getRoleId());
                if (r!=null){
                    s.setRole(r);
                }
            }
        }
        return list;
    }

    /**
     * 根据督导的id查询该督导所负责的课程列表
     * @param startPage
     * @param pageSize
     * @param id 督导id
     * @return List<Course> 课程列表
     */
    @Override
    public List<Course> listCourseBySupervisorId(int startPage, int pageSize, int id) {
        Page<Course> page = new Page<>(startPage,pageSize);
        return supervisorMapper.listCourseBySupervisorId(page,id);
    }

    /**
     * 将Supervisor转换为User便于存储和管理
     * @return user User
     */
    public User toUser(Supervisor supervisor){
        User user = new User();
        user.setId(supervisor.getId());
        user.setName(supervisor.getName());
        user.setPassword(supervisor.getPassword());
        user.setRoleId(supervisor.getRole().getId());
        user.setRole(supervisor.getRole());
        user.setIdNumber(supervisor.getIdNumber());
        return user;
    }

}
