package com.watermelon.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.watermelon.api.entity.Course;
import com.watermelon.api.entity.Supervisor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SupervisorMapper {
    List<Supervisor> getAllSupervisor();

    Supervisor getSupervisorById(int id);

    void addSupervisor(Supervisor supervisor);

    void updateSupervisor(Supervisor supervisor);

    void deleteSupervisor(int id);

    int getMaxSupervisorId();

    IPage<Supervisor> searchSupervisor(Page<Supervisor> page, String str);

    IPage<Course> listCourseBySupervisorId(Page<Course> page,int id);

    /**
     * 添加督导可评价的课程老师
     * @param supervisorId
     * @param courseId
     * @param classId
     * @return
     */
    int addSupervisorCourse(int supervisorId, int courseId, int classId);

    /**
     * 删除督导可评价的课程老师
     * @param supervisorId
     * @param courseId
     * @param classId
     * @return
     */
    int deleteSupervisorCourse(int supervisorId, int courseId, int classId);


}
