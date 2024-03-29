package com.watermelon.api.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  //非空才会序列化
public class Course {
    /**
     * id 课程号
     * name 课程名
     * courseType 课程类型
     * courseClass 课程类别
     * courseDept 课程开设学院
     * score 学分
     * time 学时
     */
    private int id;
    private String name;
    private String number;
    private String courseType;
    private String courseClass;
    private String courseDep;
    private float score;
    private int time;
    private Teacher teacher;
    /**
     * classId 在评价模块中用于记录当前课程实体(一门课程拥有多个实体)对应的班级号
     * aclass 在评价模块中用于记录当前课程实体的班级信息
     */
    private Integer classId;
    private Class aClass;
}
