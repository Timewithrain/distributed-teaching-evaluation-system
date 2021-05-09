package com.watermelon.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

/**
 * 在EvaluationController中通过@Validated注解对entity进行数据校验
 * 在Controller中依据IndividualEvaluation各字段的注解进行逻辑及数据校验
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndividualEvaluation{

//    @Null
    private Integer individualId;

    @NotNull
    private Integer roleId;

    @NotNull
    private Integer fromId;

    @NotNull
    private Integer teacherId;

    @NotNull
    private Integer courseId;

//    @Min(value=2)
//    @Max(value=5)
    private Integer score1;

//    @Min(value=2)
//    @Max(value=5)
    private Integer score2;

//    @Min(value=2)
//    @Max(value=5)
    private Integer score3;

//    @Min(value=2)
//    @Max(value=5)
    private Integer score4;

//    @Min(value=2)
//    @Max(value=5)
    private Integer score5;

//    @Min(value=2)
//    @Max(value=5)
    private Integer score6;

//    @Null
    private Double totalScore;

    private String advice;

}
