package com.watermelon.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.watermelon.api.entity.Class;
import com.watermelon.api.entity.Course;
import com.watermelon.api.service.ClassService;
import com.watermelon.api.service.CourseService;
import com.watermelon.mapper.ClassMapper;
import com.watermelon.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ClassMapper classMapper;

    @Override
    public Course getCourseById(int id) {
        return courseMapper.getCourseById(id);
    }

    @Override
    public Course getCourseByName(String name) {
        return courseMapper.getCourseByName(name);
    }

    @Override
    public IPage<Course> listCourse(int startPage, int pageSize) {
        Page<Course> page = new Page<>(startPage,pageSize);
        IPage<Course> courseIPage = courseMapper.listCourse(page);
        for (Course c : courseIPage.getRecords()) {
            Class aClass = classMapper.getClassById(c.getClassId());
            c.setAClass(aClass);
        }
        return courseIPage;
    }

    @Override
    public List<Course> listAllCourse() {
        return courseMapper.listCourse();
    }

    @Override
    public List<Course> listAllCourseByClassId(int classId) {
        return courseMapper.listAllCourseByClassId(classId);
    }

    @Override
    public IPage<Course> listCourseByTeacherId(int startPage, int pageSize, int teacherId) {
        Page<Course> page = new Page<>(startPage,pageSize);
        IPage<Course> courseIPage = courseMapper.listCourseByTeacherId(page, teacherId);
        for (Course c : courseIPage.getRecords()) {
            Class aClass = classMapper.getClassById(c.getClassId());
            c.setAClass(aClass);
        }
        return courseIPage;
    }

    @Override
    public IPage<Course> listCourseByClassId(int startPage, int pageSize, int classId) {
        Page<Course> page = new Page<>(startPage,pageSize);
        return courseMapper.listCourseByClassId(page,classId);
    }

    @Override
    public IPage<Course> listCourseByDepartmentId(int startPage, int pageSize, int departmentId){
        Page<Course> page = new Page<>(startPage,pageSize);
        return courseMapper.listCourseByDepartmentId(page,departmentId);
    }

    @Override
    public IPage<Course> listCourseWithoutTeacher(int startPage, int pageSize) {
        Page<Course> page = new Page<>(startPage,pageSize);
        return courseMapper.listCourseWithoutTeacher(page);
    }

    @Override
    public IPage<Course> searchCourseWithoutTeacher(int startPage, int pageSize, String str, String courseClass, String departmentId) {
        Page<Course> page = new Page<>(startPage,pageSize);
        return courseMapper.searchCourseWithoutTeacher(page, str, courseClass, departmentId);
    }

    @Override
    public IPage<Course> searchCourseNotSelected(int startPage, int pageSize, String str, int classId) {
        Page<Course> page = new Page<>(startPage,pageSize);
        Integer id = new Integer(classId);
        if (id.intValue()<=0) id = 0;
        return courseMapper.searchCourseNotSelected(page, str, id);
    }

    public IPage<Course> searchCourseBySupervisorId(int startPage, int pageSize, String supervisorId, String classId, String str) {
        Page<Course> page = new Page<>(startPage,pageSize);
        IPage<Course> courseIPage = courseMapper.searchCourseBySupervisorId(page,supervisorId,classId,str);
        //为课程添加对应班级
        List<Course> list = courseIPage.getRecords();
        for (Course c : list) {
            Class aClass = classMapper.getClassById(c.getClassId());
            c.setAClass(aClass);
        }
        courseIPage.setRecords(list);
        return courseIPage;
    }

    @Override
    public int addCourse(Course course) {
        course.setId(courseMapper.getMaxCourseId()+1);
        return courseMapper.addCourse(course);
    }

    @Override
    public void updateCourse(Course course) {
        courseMapper.updateCourse(course);
    }

    @Override
    public void deleteCourse(int id) {
        //由于课程在class_course表中有外键约束，因此需要先删除class_course表中的course记录
        courseMapper.deleteClassCourse(id);
        courseMapper.deleteCourse(id);
    }

    @Override
    public void deleteCourseByDepartmentId(int departmentId) {
        //根据学院id删除课程，删除课程前同时删除class_course表中的记录
        courseMapper.deleteCourseByDepartmentId(departmentId);
    }

    @Override
    public int getMaxCourseId() {
        return courseMapper.getMaxCourseId();
    }

    @Override
    public int getCourseNumber() {
        return courseMapper.getCourseNumber();
    }
}
