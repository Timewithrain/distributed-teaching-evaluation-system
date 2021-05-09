package com.watermelon.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SummaryEvaluation {

    /**
     * classId  班级号
     * courseId 课程(包含课程号)
     * semester  学期
     * teacherScore  教师总评分数
     * supervisorScore 督导总评分数
     * studentScore  学生总评分数
     * summaryScore  综合总评分数
     * teacherEvaluationList  该评价包含的教师所作的评价列表(空)
     * supervisorEvaluationList  该评价包含的督导所作的评价列表
     * studentEvaluationList  该评价包含的学生所作的评价列表
     */
    private Integer id;
    private Integer classId;
    private Course course;
    private String semester;
    private Float teacherScore;
    private Float supervisorScore;
    private Float studentScore;
    private Float summaryScore;
    private List<IndividualEvaluation> teacherEvaluationList;
    private List<IndividualEvaluation> supervisorEvaluationList;
    private List<IndividualEvaluation> studentEvaluationList;

}
