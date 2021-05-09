package com.watermelon.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.watermelon.api.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DepartmentMapper {

    Department getDepartmentById(int id);

    Department getDepartmentByName(String name);

    void addDepartment(Department department);

    IPage<Department> listDepartment(Page<Department> page);

    List<Department> listDepartment();

    IPage<Department> searchDepartment(Page<Department> page,String str);

    void updateDepartment(Department department);

    void deleteDepartment(int id);

    int getMaxDepartmentId();

    int getDepartmentNumber();

    int getClassNumberByDepartmentId(int departmentId);

    int getCourseNumberByDepartmentId(int departmentId);

    int getTeacherNumberByDepartmentId(int departmentId);

    int getStudentNumberByDepartmentId(int departmentId);
}
