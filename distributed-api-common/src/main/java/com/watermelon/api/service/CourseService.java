package com.watermelon.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.watermelon.api.entity.Course;

import java.util.List;

public interface CourseService {

    Course getCourseById(int id);

    Course getCourseByName(String name);

    IPage<Course> listCourse(int startPage, int pageSize);

    List<Course> listAllCourse();

    IPage<Course> listCourseByTeacherId(int startPage, int pageSize, int teacherId);

    List<Course> listAllCourseByClassId(int classId);

    IPage<Course> listCourseByClassId(int startPage, int pageSize, int classId);

    IPage<Course> listCourseWithoutTeacher(int startPage, int pageSize);

    IPage<Course> listCourseByDepartmentId(int startPage, int pageSize, int departmentId);

    IPage<Course> searchCourseWithoutTeacher(int startPage, int pageSize, String str, String courseClass, String departmentId);

    IPage<Course> searchCourseNotSelected(int startPage, int pageSize, String str, int classId);

    IPage<Course> searchCourseBySupervisorId(int startPage, int pageSize, String supervisorId, String classId, String str);

    int addCourse(Course course);

    void updateCourse(Course course);

    void deleteCourse(int id);

    public void deleteCourseByDepartmentId(int departmentId);

    int getMaxCourseId();

    int getCourseNumber();

}
