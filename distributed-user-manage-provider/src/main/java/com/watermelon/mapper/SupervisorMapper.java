package com.watermelon.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    List<Supervisor> listSupervisor(Page<Supervisor> page);

    // 设置督导可评价的课程老师
    /**
     * 添加督导可评价的课程老师
     * @param supervisorId
     * @param courseId
     * @param teacherId
     * @return
     */
    int addSupervisorCourse(int supervisorId, int courseId, int teacherId);

    /**
     * 删除督导可评价的课程老师
     * @param supervisorId
     * @param courseId
     * @param teacherId
     * @return
     */
    int deleteSupervisorCourse(int supervisorId, int courseId, int teacherId);


}
