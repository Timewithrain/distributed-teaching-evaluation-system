package com.watermelon.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.watermelon.api.entity.Course;
import com.watermelon.api.entity.IndividualEvaluation;
import com.watermelon.api.entity.SummaryEvaluation;
import com.watermelon.api.service.EvaluationService;
import com.watermelon.mapper.EvaluationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    private EvaluationMapper evaluationMapper;

    @Autowired
    private RemoteCourseServiceImpl remoteCourseService;

    @Override
    public List<Course> getCoursesByTeacherId(int id) {
        return evaluationMapper.getCoursesByTeacherId(id);
    }

    @Override
    public List<Course> getCoursesBySuperId(int id) {
        return evaluationMapper.getCoursesBySuperId(id);
    }

    @Override
    public List<Course> getCoursesByStuId(int id) {
        return evaluationMapper.getCoursesByStuId(id);
    }

    // 分页查询课程
    @Override
    public IPage<Course> getCoursesByStuId(Integer id, Integer startPage, Integer pageSize,String courseName) {
        Page<Course> p = new Page<>(startPage,pageSize);
        p.setRecords(evaluationMapper.getCoursesByStuIdWithPage(id, p, courseName));
        return p;
    }

    @Override
    public IPage<Course> getCoursesBySuperId(Integer id, Integer startPage, Integer pageSize,String courseName) {
        Page<Course> p = new Page<>(startPage,pageSize);
        p.setRecords(evaluationMapper.getCoursesBySuperIdWithPage(id, p, courseName));
        return p;
    }

    @Override
    public IPage<Course> getCoursesByTeacherId(Integer id, Integer startPage, Integer pageSize,String courseName){
        if(id == null) {
            throw new NullPointerException();
        }
        Page<Course> p = new Page<>(startPage,pageSize);
        p.setRecords(evaluationMapper.getCoursesByTeacherIdWithPage(id, p, courseName));
        return p;
    }

    @Override
    public IPage getCoursesByAdmin(Integer startPage, Integer pageSize, String courseName) {
        Page p = new Page<>(startPage,pageSize);
        p.setRecords(evaluationMapper.getCoursesByAdminWithPage(p,courseName));
        return p;
    }

    // 获取个人评价
    @Override
    public IndividualEvaluation getSuperIndiEvaluation(int supervisorId, int teacherId, int courseId) {
        return evaluationMapper.getSuperIndiEvaluation(supervisorId,teacherId,courseId);
    }

    @Override
    public IndividualEvaluation getTeacherIndiEvaluation(int fromTeacherId, int toTeacherId, int courseId) {
        return evaluationMapper.getTeacherIndiEvaluation(fromTeacherId,toTeacherId,courseId);
    }

    @Override
    public IndividualEvaluation getStudentIndiEvaluation(int studentId, int teacherId, int courseId) {
        return evaluationMapper.getStudentIndiEvaluation(studentId,teacherId,courseId);
    }

    @Override
    public int addIndividualEvaluation(IndividualEvaluation individualEvaluation) {
        return evaluationMapper.addIndividualEvaluation(individualEvaluation);
    }

    @Override
    public List<Map> listIndividualEvaluation(int teacherId,int courseId) {
        return null;
    }

    @Override
    public IPage<Course> getCoursesOfTeacher(int teacherId, int startPage, int pageSize) {
        Page<Course> p = new Page<>(startPage,pageSize);
        p.setRecords(evaluationMapper.getCoursesOfTeacher(teacherId, p));
        return p;
    }

    @Override
    public IPage getAdvices(int teacherId, int courseId, int roleId, int startPage, int pageSize) {
        Page p = new Page<>(startPage,pageSize);
        p.setRecords(evaluationMapper.getAdvices(teacherId, courseId,roleId,p));
        return p;
    }

    @Override
    public Integer ifEvaluated(Integer fromId, Integer teacherId, Integer courseId) {
        return evaluationMapper.ifEvaluated(fromId,teacherId,courseId);
    }

    /*********************************已完成********************************/

    @Override
    public IPage<SummaryEvaluation> listSummaryEvaluation(int startPage, int pageSize) {
        IPage<Course> coursePage = remoteCourseService.listCourse(startPage,pageSize);
        List<SummaryEvaluation> evaluationList = new ArrayList<>();
        int countId = 1;
        for (Course course : coursePage.getRecords()) {
            SummaryEvaluation evaluation = calculateSummaryEvaluation(course.getClassId(), course);
            evaluation.setId(countId++);
            evaluationList.add(evaluation);
        }
        IPage<SummaryEvaluation> evaluationPage = new Page<>(startPage, pageSize);
        evaluationPage.setTotal(coursePage.getTotal());
        evaluationPage.setRecords(evaluationList);
        return evaluationPage;
    }

    @Override
    public IPage<SummaryEvaluation> listSummaryEvaluationByTeacherId(int startPage, int pageSize, int teacherId) {
        IPage<Course> coursePage = remoteCourseService.listCourseByTeacherId(startPage,pageSize,teacherId);
        List<SummaryEvaluation> evaluationList = new ArrayList<>();
        int countId = 1;
        for (Course course : coursePage.getRecords()) {
            SummaryEvaluation evaluation = calculateSummaryEvaluation(course.getClassId(), course);
            evaluation.setId(countId++);
            evaluationList.add(evaluation);
        }
        IPage<SummaryEvaluation> evaluationPage = new Page<>(startPage, pageSize);
        evaluationPage.setTotal(coursePage.getTotal());
        evaluationPage.setRecords(evaluationList);
        return evaluationPage;
    }


    /**
     * 根据班级号获取该班级所有课程的总评(分页)
     * @param startPage
     * @param pageSize
     * @param classId
     * @return
     */
    @Override
    public IPage<SummaryEvaluation> listSummaryEvaluationByClassId(int startPage, int pageSize, int classId) {
        IPage<Course> coursePage = remoteCourseService.listCourseByClassId(startPage,pageSize,classId);
        List<Course> courseList = coursePage.getRecords();
        List<SummaryEvaluation> evaluationList = new ArrayList<>();
        int countId = 1;
        for (Course course : courseList) {
            SummaryEvaluation evaluation = calculateSummaryEvaluation(classId, course);
            evaluation.setId(countId++);
            evaluationList.add(evaluation);
        }
        IPage<SummaryEvaluation> evaluationPage = new Page<>(startPage, pageSize);
        evaluationPage.setTotal(coursePage.getTotal());
        evaluationPage.setRecords(evaluationList);
        return evaluationPage;
    }

    @Override
    public IPage<SummaryEvaluation> listSummaryEvaluationBySupervisorId(int startPage, int pageSize, int supervisorId) {
        IPage<Course> coursePage = remoteCourseService.listCourseBySupervisorId(startPage,pageSize,supervisorId);
        List<Course> courseList = coursePage.getRecords();
        List<SummaryEvaluation> evaluationList = new ArrayList<>();
        int countId = 1;
        for (Course course : courseList) {
            SummaryEvaluation evaluation = calculateSummaryEvaluation(course.getClassId(), course);
            evaluation.setId(countId++);
            evaluationList.add(evaluation);
        }
        IPage<SummaryEvaluation> evaluationPage = new Page<>(startPage, pageSize);
        evaluationPage.setTotal(coursePage.getTotal());
        evaluationPage.setRecords(evaluationList);
        return evaluationPage;
    }


    /**
     * 根据班级和课程号获取对应的个人评价并生成
     * @param classId
     * @param course
     * @return
     */
    private SummaryEvaluation calculateSummaryEvaluation(Integer classId, Course course) {
        SummaryEvaluation summaryEvaluation = new SummaryEvaluation();
        summaryEvaluation.setClassId(classId);
        summaryEvaluation.setCourse(course);
        //根据班级和课程号获取学生评价
        List<IndividualEvaluation> list = evaluationMapper.listEvaluationByClassIdAndCourseIdAndRoleId(classId,course.getId(),3);
        summaryEvaluation.setStudentEvaluationList(list);
        //计算
        float totalStudentScore = 0;
        for (IndividualEvaluation evaluation : list) {
            totalStudentScore += evaluation.getTotalScore();
        }
        if (totalStudentScore!=0){
            totalStudentScore = totalStudentScore / list.size();
            summaryEvaluation.setStudentScore(totalStudentScore);
        } else {
            summaryEvaluation.setStudentScore(0f);
        }
        //根据班级和课程号获取督导评价
        List<IndividualEvaluation> list2 = evaluationMapper.listEvaluationByClassIdAndCourseIdAndRoleId(classId,course.getId(),4);
        summaryEvaluation.setSupervisorEvaluationList(list2);
        float totalSupervisorScore = 0;
        for (IndividualEvaluation evaluation : list2) {
            totalSupervisorScore += evaluation.getTotalScore();
        }
        if (totalSupervisorScore!=0) {
            totalSupervisorScore = totalSupervisorScore / list2.size();
            summaryEvaluation.setSupervisorScore(totalSupervisorScore);
        } else {
            summaryEvaluation.setSupervisorScore(0f);
        }
        //综合学生以及督导的评价结果生成总评,生成策略：学生及督导权重各占一半，若任意一方尚未评价则有评价的一方占100%的权重
        float totalSummaryScore;
        if (totalStudentScore!=0 && totalSupervisorScore!=0) {
            totalSummaryScore = (totalStudentScore + totalSupervisorScore)/2;
        } else {
            totalSummaryScore = totalStudentScore + totalSupervisorScore;
        }
        summaryEvaluation.setSummaryScore(totalSummaryScore);
        return summaryEvaluation;
    }

}
