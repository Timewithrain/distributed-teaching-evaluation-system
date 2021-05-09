package com.watermelon.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.watermelon.api.entity.Course;
import com.watermelon.api.entity.Teacher;

import java.util.List;

public interface TeacherService {

    Teacher getTeacherById(int id);

    void addTeacher(Teacher teacher);

    void updateTeacher(Teacher teacher);

    void deleteTeacher(int id);

    void deleteTeacherByDepartmentId(int departmentId);

    IPage<Teacher> listTeacher(int startPage, int pageSize);

    List<Teacher> listAllTeacher();

    List<Teacher> listTeacherByDepartmentId(int departmentId);

    IPage<Course> listCourseByTeacherId(int startPage, int pageSize, int teacherId);

    List<Teacher> searchAllTeacher(String str, String departmentId);

    IPage<Teacher> searchTeacher(int startPage, int pageSize, String str, String departmentId);
}
