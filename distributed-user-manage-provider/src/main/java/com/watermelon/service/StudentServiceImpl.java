package com.watermelon.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.watermelon.api.entity.Class;
import com.watermelon.api.entity.Course;
import com.watermelon.api.entity.Role;
import com.watermelon.api.entity.Student;
import com.watermelon.api.entity.User;
import com.watermelon.api.service.RoleService;
import com.watermelon.api.service.StudentService;
import com.watermelon.mapper.StudentMapper;
import com.watermelon.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RemoteCourseService RemoteCourseService;

    @Override
    public Student getStudentById(int id) {
        Student student = studentMapper.getStudentById(id);
        student = addRoleFieldOfStudent(student);
        student = addCourseListFieldOfStudent(student);
        return student;
    }

    @Override
    public void addStudent(Student student) {
        student.setId(studentMapper.getMaxUserId()+1);
        userMapper.addUser(toUser(student));
        studentMapper.addStudent(student);
    }

    @Override
    public void updateStudent(Student student) {
        userMapper.updateUser(toUser(student));
        studentMapper.updateStudent(student);
    }

    @Override
    public void deleteStudent(int id) {
        studentMapper.deleteStudent(id);
        userMapper.deleteUser(id);
    }

    @Override
    public List<Student> listStudentWithNoCourse(int startPage, int pageSize) {
        Page<Course> page = new Page<>(startPage,pageSize);
        List<Student> list = studentMapper.listStudentWithNoCourse(page);
        for (Student s : list){
            s = addRoleFieldOfStudent(s);
        }
        return list;
    }

    @Override
    public List<Student> listStudent(int startPage, int pageSize) {
        List<Student> list = studentMapper.listStudentWithNoCourse(new Page<>(startPage,pageSize));
        for (Student s : list){
            s = addRoleFieldOfStudent(s);
            s = addCourseListFieldOfStudent(s);
        }
        return list;
    }

    @Override
    public List<Student> listStudentByClassId(int startPage, int pageSize,int calssId) {
        Page<Course> page = new Page<>(startPage,pageSize);
        List<Student> list = studentMapper.listStudentByClassId(page,calssId);
        for (Student s : list){
            s = addRoleFieldOfStudent(s);
            s = addCourseListFieldOfStudent(s);
        }
        return list;
    }

    @Override
    public List<Student> searchStudent(int startPage, int pageSize, String str) {
        List<Student> list = studentMapper.searchStudent(new Page<>(startPage,pageSize),str);
        for (Student s : list){
            s = addRoleFieldOfStudent(s);
            s = addCourseListFieldOfStudent(s);
        }
        return list;
    }

    /**
     * 将Student转换为User便于存储和管理
     * @return user User
     */
    public User toUser(Student student){
        User user = new User();
        user.setId(student.getId());
        user.setName(student.getName());
        user.setPassword(student.getPassword());
        user.setRoleId(student.getRole().getId());
        user.setRole(student.getRole());
        user.setIdNumber(student.getIdNumber());
        return user;
    }

    /**
     * 传入Student，根据学生id从数据库中补全Student的User及Role字段信息
     * @param s Student
     * @return s Student
     */
    private Student addRoleFieldOfStudent(Student s){
        User u = userMapper.getUserById(s.getId());
        if (u!=null){
            s.addUserInfo(u);
            Role r = roleService.getRoleById(s.getRoleId());
            if (r!=null){
                s.setRole(r);
            }
        }
        return s;
    }

    /**
     * 传入Student，根据学生班级id添加学生的CourseList课程列表字段信息
     * @param s Student
     * @return s Student
     */
    private Student addCourseListFieldOfStudent(Student s){
        //通过courseMapper根据学生的班级号获取课程，并添加进学生的courseList属性
        Class aClass = s.getAClass();
        if (aClass!=null){
            int classId = aClass.getId();
            List<Course> courseList =  RemoteCourseService.listCourseByClassId(classId);
            s.setCourseList(courseList);
        }
        return s;
    }

}
