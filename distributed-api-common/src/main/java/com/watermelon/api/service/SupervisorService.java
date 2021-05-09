package com.watermelon.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.watermelon.api.entity.Course;
import com.watermelon.api.entity.Supervisor;

import java.util.List;

public interface SupervisorService {

    List<Supervisor> getAllSupervisor();

    // 设置督导可评价的课程
    int addSupervisorCourse(int supervisorId, int courseId, int classId);

    void addSupervisorClass(int supervisorId, int classId);

    int deleteSupervisorCourse(int supervisorId, int courseId, int classId);

    Supervisor getSupervisorById(int id);

    void addSupervisor(Supervisor supervisor);

    void updateSupervisor(Supervisor supervisor);

    void deleteSupervisor(int id);

    IPage<Supervisor> searchSupervisor(int startPage, int pageSize, String str);

    IPage<Course> listCourseBySupervisorId(int startPage, int pageSize, int id);

}
