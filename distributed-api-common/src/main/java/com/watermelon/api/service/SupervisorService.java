package com.watermelon.api.service;

import com.watermelon.api.entity.Supervisor;

import java.util.List;

public interface SupervisorService {

    List<Supervisor> getAllSupervisor();

    // 设置督导可评价的课程
    int addSupervisorCourse(int supervisorId, int courseId, int teacherId);

    int deleteSupervisorCourse(int supervisorId, int courseId, int teacherId);

    Supervisor getSupervisorById(int id);

    void addSupervisor(Supervisor supervisor);

    void updateSupervisor(Supervisor supervisor);

    void deleteSupervisor(int id);

    List<Supervisor> listSupervisor(int startPage, int pageSize);

}
