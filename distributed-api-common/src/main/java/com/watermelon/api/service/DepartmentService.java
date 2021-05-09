package com.watermelon.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.watermelon.api.entity.Department;

import java.util.List;

public interface DepartmentService {

    void addDepartment(Department department);

    IPage<Department> listDepartment(int startPage, int pageSize);

    List<Department> listAllDepartment();

    IPage<Department> searchDepartment(int startPage, int pageSize, String str);

    void updateDepartment(Department department);

    void deleteDepartment(int id);

    int getMaxDepartmentId();

    int getDepartmentNumber();
}
