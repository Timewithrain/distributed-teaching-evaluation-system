package com.watermelon.api.service;

import com.watermelon.api.entity.Department;

import java.util.List;

public interface DepartmentService {

    void addDepartment(Department department);

    List<Department> listDepartment(int startPage, int pageSize);

    void updateDepartment(Department department);

    void deleteDepartment(int id);

    int getMaxDepartmentId();
}
