package com.watermelon.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.watermelon.api.entity.Course;
import com.watermelon.api.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TeacherMapper {

    Teacher getTeacherById(int id);

    void addTeacher(Teacher teacher);

    void addTeacherDept(int id,int deptId);

    void updateTeacher(Teacher teacher);

    void deleteTeacher(int id);

    int getMaxUserId();

    List<Teacher> listTeacher(Page<Teacher> page);

    List<Teacher> listTeacher();

    List<Course> listCourseByTeacherId(Page<Course> page,int teacherId);
}
