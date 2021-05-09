package com.watermelon.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.watermelon.api.entity.Student;

import java.util.List;

public interface StudentService {

    Student getStudentById(int id);

    void addStudent(Student student);

    void updateStudent(Student student);

    void deleteStudent(int id);

    void deleteStudentByClassId(int classId);

    IPage<Student> listStudentWithNoCourse(int startPage, int pageSize);

    IPage<Student> listStudent(int startPage, int pageSize);

    IPage<Student> listStudentByClassId(int startPage, int pageSize,int classId);

    IPage<Student> searchStudent(int startPage, int pageSize, String str, String classId, String departmentId);

}
