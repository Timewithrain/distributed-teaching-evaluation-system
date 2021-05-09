package com.watermelon.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.watermelon.api.entity.Class;

import java.util.List;

public interface ClassService {

    Class getClassById(int id);

    Class getClassByName(String name);

    IPage<Class> listClassWithNoCourse(int startPage, int pageSize);

    IPage<Class> listClass(int startPage, int pageSize);

    IPage<Class> searchClass(int startPage, int pageSize, String str, String grade, String departmentId);

    List<Class> listClassByDepartmentId(int departmentId);

    List<String> listGrade();

    int addClass(Class aClass);

    void updateClass(Class aClass);

    void deleteClass(int id);

    void addClassCourse(int classId,int courseId, int teacherId);

    void deleteClassCourse(int classId,int courseId);

    int getMaxClass();

    int getClassNumber();
}
