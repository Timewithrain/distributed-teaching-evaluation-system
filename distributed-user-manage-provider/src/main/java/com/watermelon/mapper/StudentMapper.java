package com.watermelon.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.watermelon.api.entity.Course;
import com.watermelon.api.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StudentMapper {

    Student getStudentById(int id);

    void addStudent(Student student);

    void updateStudent(Student student);

    void deleteStudent(int id);

    int getMaxUserId();

    List<Student> listStudentWithNoCourse();

    IPage<Student> listStudentWithNoCourse(Page<Course> page);

    List<Student> listStudentByClassId(int classId);

    IPage<Student> listStudentByClassId(Page<Course> page, int classId);

    IPage<Student> searchStudent(Page<Course> page,String str, String classId, String departmentId);

}
