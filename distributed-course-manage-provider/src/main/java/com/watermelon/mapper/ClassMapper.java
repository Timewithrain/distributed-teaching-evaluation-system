package com.watermelon.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.watermelon.api.entity.Class;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ClassMapper {

    Class getClassById(int id);

    Class getClassByName(String name);

    IPage<Class> listClass(Page<Class> page);

    List<Class> listClassByDepartmentId(int departmentId);

    IPage<Class> searchClass(Page<Class> page, String str, String grade, String departmentId);

    List<String> listGrade();

    int addClass(Class aClass);

    void updateClass(Class aClass);

    void deleteClass(int id);

    void addClassCourse(int classId, int courseId, int teacherId);

    void deleteClassCourse(int classId, int courseId);

    void deleteClassFromClassCourse(int classId);

    int getMaxClassId();

    int getClassNumber();
}
