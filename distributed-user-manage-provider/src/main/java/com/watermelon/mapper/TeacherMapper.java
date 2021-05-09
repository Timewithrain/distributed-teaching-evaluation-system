package com.watermelon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.watermelon.api.entity.Course;
import com.watermelon.api.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TeacherMapper extends BaseMapper<Teacher> {

    Teacher getTeacherById(int id);

    void addTeacher(Teacher teacher);

    void addTeacherDept(int id,int deptId);

    void updateTeacher(Teacher teacher);

    void deleteTeacher(int id);

    int getMaxUserId();

    IPage<Teacher> listTeacher(IPage<Teacher> page);

    List<Teacher> listTeacher();

    IPage<Course> listCourseByTeacherId(IPage<Course> page,int teacherId);

    List<Teacher> listTeacherByDepartmentId(int departmentId);

    List<Teacher> searchTeacher(String str, String departmentId);

    IPage<Teacher> searchTeacher(IPage<Teacher> page, String str, String departmentId);
}
