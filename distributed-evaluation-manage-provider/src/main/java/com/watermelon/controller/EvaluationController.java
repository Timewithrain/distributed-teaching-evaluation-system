package com.watermelon.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.watermelon.api.entity.IndividualEvaluation;
import com.watermelon.api.entity.SummaryEvaluation;
import com.watermelon.api.entity.User;
import com.watermelon.api.service.EvaluationService;
import com.watermelon.exception.MyException;
import com.watermelon.api.util.StatusCode;
import com.watermelon.api.util.ResultUtil;
import com.watermelon.service.RemoteCourseServiceImpl;
import com.watermelon.service.RemoteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.text.DecimalFormat;
import java.util.*;

@RestController
@RequestMapping("/evaluation")
@Validated
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private RemoteUserService userService;

    @Autowired
    private RemoteCourseServiceImpl courseService;


    /**
     * 获取督导本人所作过的评价，根据督导、教师、课程唯一确定一条评价
     * @param teacherId 教师Id
     * @param courseId 课程Id
     * @return IndividualEvaluation
     */
    @GetMapping("/superIndividualEvaluation")
    public Object listSuperIndividualEvaluation(int teacherId, int courseId,HttpSession session){
        User user = userService.getUserByName((String) session.getAttribute("username"));
        if(user == null) {
            ResultUtil resultUtil = ResultUtil.error(StatusCode.UNAUTHORIZED);
            throw new MyException(resultUtil);
        }
        return ResultUtil.success(evaluationService.getSuperIndiEvaluation(user.getId(),teacherId,courseId));
    }

    /**
     * 获取教师本人所作过的评价，根据自身Id、被评价教师Id、课程Id唯一确定一条评价，自身Id通过session获取
     * @param teacherId 被评价的教师Id
     * @param courseId 课程Id
     * @return IndividualEvaluation
     */
    @GetMapping("/teacherIndividualEvaluation")
    public Object findTeacherIndividualEvaluation(int teacherId,int courseId,HttpSession session){
        User user = userService.getUserByName((String) session.getAttribute("username"));
        if(user == null) {
            ResultUtil resultUtil = ResultUtil.error(StatusCode.UNAUTHORIZED);
            throw new MyException(resultUtil);
        }
        return ResultUtil.success(evaluationService.getTeacherIndiEvaluation(user.getId(),teacherId,courseId));
    }

    /**
     * 获取学生本人所作过的评价，根据学生Id、教师Id、课程Id唯一确定一条评价
     * @param teacherId 教师Id
     * @param courseId 课程Id
     * @return IndividualEvaluation
     */
    @GetMapping("/listStudentIndividualEvaluation")
    public Object listStudentIndividualEvaluation(int teacherId,int courseId,HttpSession session){
        User user = userService.getUserByName((String) session.getAttribute("username"));
        if(user == null) {
            ResultUtil resultUtil = ResultUtil.error(StatusCode.UNAUTHORIZED);
            throw new MyException(resultUtil);
        }
        return ResultUtil.success(evaluationService.getStudentIndiEvaluation(user.getId(),teacherId,courseId));
    }

    /***************************** 已完善 *****************************/
    /**
     * 添加学生的个人评价
     * @param individualEvaluation 学生评价
     * @return
     */
    @PostMapping("/addStudentIndividualEvaluation")
    public ResultUtil addStudentIndividualEvaluation(@RequestBody @Valid IndividualEvaluation individualEvaluation) {
        evaluationService.addIndividualEvaluation(individualEvaluation);
        return ResultUtil.success();
    }

    /**
     * 不使用
     * 添加教师个人评价
     * @param individualEvaluation 教师评价
     * @param session 会话
     * @return
     */
    @PostMapping("/teacherIndividualEvaluation")
    public Object addTeacherIndividualEvaluation(@RequestBody @Valid IndividualEvaluation individualEvaluation,HttpSession session){
        // 用户未登录抛出异常
        if(session.getAttribute("username") == null) {
            ResultUtil resultUtil = ResultUtil.error(StatusCode.UNAUTHORIZED);
            throw new MyException(resultUtil);
        }
        User user = userService.getUserByName((String) session.getAttribute("username"));
        individualEvaluation.setFromId(user.getId());
        individualEvaluation.setTotalScore(((double)(individualEvaluation.getScore1() + individualEvaluation.getScore2() + individualEvaluation.getScore3() + individualEvaluation.getScore4() + individualEvaluation.getScore5() + individualEvaluation.getScore6())/6));
        // 用户已评价抛出异常
        Integer fromId = individualEvaluation.getFromId();
        Integer teacherId = individualEvaluation.getTeacherId();
        Integer courseId = individualEvaluation.getCourseId();
        if(evaluationService.ifEvaluated(fromId,teacherId,courseId) >= 1){
            ResultUtil resultUtil = ResultUtil.error(StatusCode.EXCEPTION_ERROR,"此评价已完成，不可修改");
            throw new MyException(resultUtil);
        }
        evaluationService.addIndividualEvaluation(individualEvaluation);
        return ResultUtil.success();
    }

    /***************************** 已完善 *****************************/
    /**
     * 添加督导评价
     * @param individualEvaluation 督导评价
     * @return
     */
    @PostMapping("/addSupervisorIndividualEvaluation")
    public Object addSupervisorIndividualEvaluation(@RequestBody @Valid IndividualEvaluation individualEvaluation){
        evaluationService.addIndividualEvaluation(individualEvaluation);
        return ResultUtil.success();
    }

    /**
     * 获取某教师对应的某课程总评价列表，由教师Id、课程Id确定一组评价信息，
     * 返回该课程的学生总评价、教师总评价、督导总评价
     * <p>总评价计算方式：根据角色将评价记录分为学生、督导、教师三组，
     * 分组汇总评价的各个分项并取平均值(保留至小数点后两位)
     * @param courseId 课程Id
     * @param session 会话
     * @return List<Map>
     */
    @GetMapping("/summaryEvaluation/byTeacherId")
    public Object listSummaryEvaluation(int courseId,HttpSession session){
        User user = userService.getUserByName((String) session.getAttribute("username"));
        if(user == null) {
            ResultUtil resultUtil = ResultUtil.error(StatusCode.UNAUTHORIZED);
            throw new MyException(resultUtil);
        }
        return ResultUtil.success(evaluationService.listSummaryEvaluation(user.getId(),courseId));
    }

    /**
     * 管理员通过教师id获取总评价信息，教师id、课程id确定一组评价记录，组别依据评价者角色区分
     * @param teacherId 教师id
     * @param courseId 课程id
     * @return List<Map>
     */
    @GetMapping("/admin/summaryEvaluation/byTeacherId")
    public Object listSummaryEvaluationByTeacherId(Integer teacherId,Integer courseId){
        return ResultUtil.success(evaluationService.listSummaryEvaluation(teacherId,courseId));
    }

    /***************************** 已完善 *****************************/
    /**
     * 管理员获取该所有课程的总评(分页)
     * 总评中包含个人评价
     * @param startPage
     * @param pageSize
     * @return
     */
    @GetMapping("/listSummaryEvaluation")
    public ResultUtil listSummaryEvaluation(int startPage, int pageSize){
        IPage<SummaryEvaluation> evaluationPage = evaluationService.listSummaryEvaluation(startPage, pageSize);
        Map<String,Object> map = new HashMap<>();
        map.put("list",evaluationPage.getRecords());
        map.put("total",evaluationPage.getTotal());
        return ResultUtil.success(map);
    }

    /***************************** 已完善 *****************************/
    /**
     * 教师获取该自己所授课程的总评(分页)
     * 总评中包含个人评价
     * @param startPage
     * @param pageSize
     * @return
     */
    @GetMapping("/listSummaryEvaluationByTeacherId")
    public ResultUtil listSummaryEvaluationByTeacherId(int startPage, int pageSize, int teacherId){
        IPage<SummaryEvaluation> evaluationPage = evaluationService.listSummaryEvaluationByTeacherId(startPage, pageSize, teacherId);
        Map<String,Object> map = new HashMap<>();
        map.put("list",evaluationPage.getRecords());
        map.put("total",evaluationPage.getTotal());
        return ResultUtil.success(map);
    }

    /***************************** 已完善 *****************************/
    /**
     * 学生根据班级ID获取该班级所有课程的总评(分页)
     * 总评中包含个人评价
     * @param startPage
     * @param pageSize
     * @param classId
     * @return
     */
    @GetMapping("/listSummaryEvaluationByClassId")
    public ResultUtil listSummaryEvaluationByClassId(int startPage, int pageSize, int classId){
        IPage<SummaryEvaluation> evaluationPage = evaluationService.listSummaryEvaluationByClassId(startPage, pageSize, classId);
        Map<String,Object> map = new HashMap<>();
        map.put("list",evaluationPage.getRecords());
        map.put("total",evaluationPage.getTotal());
        return ResultUtil.success(map);
    }

    /***************************** 已完善 *****************************/
    /**
     * 根据督导ID获取该督导的所有课程的总评(分页)
     * 总评中包含个人评价
     * @param startPage
     * @param pageSize
     * @param supervisorId
     * @return
     */
    @GetMapping("/listSummaryEvaluationBySupervisorId")
    public ResultUtil listSummaryEvaluationBySupervisorId(int startPage, int pageSize, int supervisorId){
        IPage<SummaryEvaluation> evaluationPage = evaluationService.listSummaryEvaluationBySupervisorId(startPage, pageSize, supervisorId);
        Map<String,Object> map = new HashMap<>();
        map.put("list",evaluationPage.getRecords());
        map.put("total",evaluationPage.getTotal());
        return ResultUtil.success(map);
    }
}
