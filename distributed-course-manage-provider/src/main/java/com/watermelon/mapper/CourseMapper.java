package com.watermelon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.watermelon.api.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CourseMapper {

    Course getCourseById(int id);

    Course getCourseByName(String name);

    IPage<Course> listCourse(Page<Course> page);

    List<Course> listCourse();

    List<Course> listAllCourseByClassId(int classId);

    IPage<Course> listCourseByTeacherId(Page<Course> page, int teacherId);

    IPage<Course> listCourseByClassId(Page<Course> page, int classId);

    IPage<Course> listCourseByDepartmentId(Page<Course> page, int departmentId);

    IPage<Course> listCourseWithoutTeacher(Page<Course> page);

    IPage<Course> searchCourse(Page<Course> page, String str);

    IPage<Course> searchCourseWithoutTeacher(Page<Course> page, String str, String courseClass, String departmentId);

    IPage<Course> searchCourseNotSelected(Page<Course> page, String str, int classId);

    IPage<Course> searchCourseBySupervisorId(Page<Course> page, String supervisorId, String classId, String str);

    int addCourse(Course course);

    void updateCourse(Course course);

    void deleteClassCourse(int id);

    void deleteCourse(int id);

    void deleteCourseByDepartmentId(int departmentId);

    int getMaxCourseId();

    int getCourseNumber();
}
