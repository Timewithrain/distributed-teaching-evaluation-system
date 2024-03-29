package com.watermelon.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.watermelon.api.entity.Course;
import com.watermelon.api.entity.IndividualEvaluation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EvaluationMapper {

    // 获取可评价的课程老师列表
    List<Course> getCoursesByTeacherId(int id);

    List<Course> getCoursesByStuId(int id);

    List<Course> getCoursesBySuperId(int id);

    List<Course> getCoursesByStuIdWithPage(int id, Page<Course> page, String courseName);

    List<Course> getCoursesByTeacherIdWithPage(int id, Page<Course> page, String courseName);

    List<Course> getCoursesBySuperIdWithPage(int id, Page<Course> page, String courseName);

    //获取对应个人评价
    /**
     * 获取督导的个人评价
     * @param superId
     * @param teacherId
     * @return
     */
    IndividualEvaluation getSuperIndiEvaluation(int superId, int teacherId, int courseId);

    /**
     * 获取教师的个人评价
     * @param fromTeacherId
     * @param toTeacherId
     * @return
     */
    IndividualEvaluation getTeacherIndiEvaluation(int fromTeacherId, int toTeacherId, int courseId);

    /**
     * 获取学生的个人评价
     * @param studentId
     * @param teacherId
     * @return
     */
    IndividualEvaluation getStudentIndiEvaluation(int studentId, int teacherId, int courseId);

    /**
     * 添加个人评价
     */
    int addIndividualEvaluation(IndividualEvaluation individualEvaluation);

    //获取教师总评价
    List<Map> getSummaryEvaluation(int teacherId,int courseId);

    //获取教师教的课程
    List<Course> getCoursesOfTeacher(int teacherId, Page<Course> p);

    //获取教师某门课的建议
    List<String> getAdvices(int teacherId,int courseId,int roleId,Page<String> p);

    //获取管理员可评价列表
    List<Course> getCoursesByAdminWithPage(Page<Course> p, String courseName);

    //查看是否已经评价
    Integer ifEvaluated(Integer fromId, Integer teacherId, Integer courseId);

    List<IndividualEvaluation> listEvaluationByClassIdAndCourseIdAndRoleId(Integer classId, Integer courseId, Integer roleId);

    IPage<IndividualEvaluation> listEvaluationByClassIdAndCourseIdAndRoleId(Page<IndividualEvaluation> page, Integer classId, Integer courseId, Integer roleId);

}