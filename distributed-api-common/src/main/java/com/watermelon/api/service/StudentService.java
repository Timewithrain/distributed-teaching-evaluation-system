package com.watermelon.api.service;

import com.watermelon.api.entity.Student;

import java.util.List;

public interface StudentService {

    Student getStudentById(int id);

    void addStudent(Student student);

    void updateStudent(Student student);

    void deleteStudent(int id);

    List<Student> listStudentWithNoCourse(int startPage, int pageSize);

    List<Student> listStudent(int startPage, int pageSize);

    List<Student> listStudentByClassId(int startPage, int pageSize,int classId);

    List<Student> searchStudent(int startPage, int pageSize, String str);

}
