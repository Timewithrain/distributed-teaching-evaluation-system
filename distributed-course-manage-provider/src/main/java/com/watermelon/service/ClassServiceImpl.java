package com.watermelon.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.watermelon.api.entity.Class;
import com.watermelon.api.entity.Course;
import com.watermelon.api.service.ClassService;
import com.watermelon.api.service.StudentService;
import com.watermelon.mapper.ClassMapper;
import com.watermelon.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ClassServiceImpl implements ClassService{

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private RemoteUserServiceImpl remoteUserService;

    @Override
    public Class getClassById(int id) {
        Class aClass = classMapper.getClassById(id);
        List<Course> courseList = courseMapper.listAllCourseByClassId(id);
        aClass.setCourseList(courseList);
        return aClass;
    }

    @Override
    public Class getClassByName(String name) {
        Class aClass = classMapper.getClassByName(name);
        List<Course> courseList = courseMapper.listAllCourseByClassId(aClass.getId());
        aClass.setCourseList(courseList);
        return aClass;
    }

    @Override
    public IPage<Class> listClassWithNoCourse(int startPage, int pageSize) {
        Page<Class> page = new Page<>(startPage,pageSize);
        return classMapper.listClass(page);
    }

    @Override
    public IPage<Class> listClass(int startPage, int pageSize) {
        Page<Class> page = new Page<>(startPage,pageSize);
        IPage<Class> list = classMapper.listClass(page);
        //通过班级号获取课程班级课程并添加至courseList
        for (Class c : list.getRecords()){
            List<Course> courseList = courseMapper.listAllCourseByClassId(c.getId());
            c.setCourseList(courseList);
        }
        return list;
    }

    @Override
    public IPage<Class> searchClass(int startPage, int pageSize, String str, String grade, String departmentId) {
        Page<Class> page = new Page<>(startPage,pageSize);
        IPage<Class> list = classMapper.searchClass(page,str,grade,departmentId);
        //通过班级号获取课程班级课程并添加至courseList
        for (Class c : list.getRecords()){
            List<Course> courseList = courseMapper.listAllCourseByClassId(c.getId());
            c.setCourseList(courseList);
        }
        return list;
    }

    @Override
    public List<Class> listClassByDepartmentId(int departmentId) {
        return classMapper.listClassByDepartmentId(departmentId);
    }

    @Override
    public List<String> listGrade() {
        return classMapper.listGrade();
    }

    @Override
    public int addClass(Class aClass) {
        aClass.setId(classMapper.getMaxClassId()+1);
        return classMapper.addClass(aClass);
    }

    @Override
    public void updateClass(Class aClass) {
        classMapper.updateClass(aClass);
    }

    @Override
    public void deleteClass(int id) {
        //首先级联删除班级对应的学生
        remoteUserService.deleteStudentByClassId(id);
        //删除class_course中的class
        classMapper.deleteClassFromClassCourse(id);
        classMapper.deleteClass(id);
    }

    /**
     * 为班级添加课程
     * @param classId
     * @param courseId
     */
    @Override
    public void addClassCourse(int classId,int courseId, int teacherId) {
        classMapper.addClassCourse(classId, courseId, teacherId);
    }

    /**
     * 删除班级已经选中的课程
     * @param classId
     * @param courseId
     */
    @Override
    public void deleteClassCourse(int classId,int courseId) {
        classMapper.deleteClassCourse(classId, courseId);
    }

    @Override
    public int getMaxClass() {
        return classMapper.getMaxClassId();
    }

    public int getClassNumber(){
        return classMapper.getClassNumber();
    }
}
