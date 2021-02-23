package com.watermelon.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.watermelon.api.entity.IndividualEvaluation;
import com.watermelon.api.entity.User;
import com.watermelon.api.service.EvaluationService;
import com.watermelon.exception.MyException;
import com.watermelon.api.util.StatusCode;
import com.watermelon.api.util.ResultUtil;
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

    /**
     * 教师获取可评价的课程列表，传入courseName课程名参数用于对课程进行模糊匹配
     * @param startPage 起始页
     * @param pageSize  单页容量
     * @param courseName 课程名
     * @param session   会话
     * @return
     */
    @GetMapping("/teacher/courses")
    public Object findEvaluCoursesOfTeacher(@NotNull Integer startPage,@NotNull Integer pageSize,String courseName,HttpSession session){
        Object username = session.getAttribute("username");
        if(username == null) {
            ResultUtil resultUtil = ResultUtil.error(StatusCode.UNAUTHORIZED);
            throw new MyException(resultUtil);
        }
        User user = userService.getUserByName((String) username);
        return ResultUtil.success(evaluationService.getCoursesByTeacherId(user.getId(),startPage,pageSize,courseName));
    }

    /**
     * 学生获取可评价的课程列表，传入课程名用于模糊匹配
     * @param startPage 起始页
     * @param pageSize 单页容量
     * @param courseName 课程名
     * @param session 会话
     * @return
     */
    @GetMapping("/student/courses")
    public Object findCoursesByStuId(Integer startPage, Integer pageSize,String courseName,HttpSession session){
        Object username = session.getAttribute("username");
        if(username == null) {
            ResultUtil resultUtil = ResultUtil.error(StatusCode.UNAUTHORIZED);
            throw new MyException(resultUtil);
        }
        User user = userService.getUserByName((String) username);
        return ResultUtil.success(evaluationService.getCoursesByStuId(user.getId(),startPage,pageSize,courseName));
    }

    /**
     * 督导获取可评价的课程列表，传入课程名用于模糊匹配，返回分页后的课程信息
     * @param startPage 起始页
     * @param pageSize 单页容量
     * @param courseName 课程名
     * @param session 会话
     * @return Page<Course>
     */
    @GetMapping("/supervisor/courses")
    public Object findTeachersBySuperId(int startPage, int pageSize,String courseName, HttpSession session){
        Object username = session.getAttribute("username");
        if(username == null) {
            ResultUtil resultUtil = ResultUtil.error(StatusCode.UNAUTHORIZED);
            throw new MyException(resultUtil);
        }
        User user = userService.getUserByName((String) username);
        return ResultUtil.success(evaluationService.getCoursesBySuperId(user.getId(),startPage,pageSize,courseName));
    }

    /**
     * 获取督导本人所作过的评价，根据督导、教师、课程唯一确定一条评价
     * @param teacherId 教师Id
     * @param courseId 课程Id
     * @return IndividualEvaluation
     */
    @GetMapping("/superIndividualEvaluation")
    public Object findSuperIndividualEvaluation(int teacherId, int courseId,HttpSession session){
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
    @GetMapping("/studentIndividualEvaluation")
    public Object findStudentIndividualEvaluation(int teacherId,int courseId,HttpSession session){
        User user = userService.getUserByName((String) session.getAttribute("username"));
        if(user == null) {
            ResultUtil resultUtil = ResultUtil.error(StatusCode.UNAUTHORIZED);
            throw new MyException(resultUtil);
        }
        return ResultUtil.success(evaluationService.getStudentIndiEvaluation(user.getId(),teacherId,courseId));
    }

    /**
     * 添加学生的个人评价
     * @param individualEvaluation 学生评价
     * @param session 会话
     * @return
     */
    @PostMapping("/studentIndividualEvaluation")
    public Object addStudentIndividualEvaluation(@RequestBody @Valid IndividualEvaluation individualEvaluation,HttpSession session){
        User user = userService.getUserByName((String) session.getAttribute("username"));
        if(user == null) {
            ResultUtil resultUtil = ResultUtil.error(StatusCode.UNAUTHORIZED);
            throw new MyException(resultUtil);
        }
        individualEvaluation.setFromId(user.getId());
        individualEvaluation.setTotalScore(((double)(individualEvaluation.getScore1() + individualEvaluation.getScore2() + individualEvaluation.getScore3() + individualEvaluation.getScore4() + individualEvaluation.getScore5() + individualEvaluation.getScore6())/6));
        evaluationService.addStudentIndiEvaluation(individualEvaluation);
        return ResultUtil.success();
    }

    /**
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
        evaluationService.addTeacherIndiEvaluation(individualEvaluation);
        return ResultUtil.success();
    }

    /**
     * 添加督导评价
     * @param individualEvaluation 督导评价
     * @param session 会话
     * @return
     */
    @PostMapping("/superIndividualEvaluation")
    public Object addSuperIndividualEvaluation(@RequestBody @Valid IndividualEvaluation individualEvaluation,HttpSession session){
        User user = userService.getUserByName((String) session.getAttribute("username"));
        if(user == null) {
            ResultUtil resultUtil = ResultUtil.error(StatusCode.UNAUTHORIZED);
            throw new MyException(resultUtil);
        }
        individualEvaluation.setFromId(user.getId());
        individualEvaluation.setTotalScore(((double)(individualEvaluation.getScore1() + individualEvaluation.getScore2() + individualEvaluation.getScore3() + individualEvaluation.getScore4() + individualEvaluation.getScore5() + individualEvaluation.getScore6())/6));
        evaluationService.addSuperIndiEvaluation(individualEvaluation);
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
    public Object findSummaryEvaluation(int courseId,HttpSession session){
        User user = userService.getUserByName((String) session.getAttribute("username"));
        if(user == null) {
            ResultUtil resultUtil = ResultUtil.error(StatusCode.UNAUTHORIZED);
            throw new MyException(resultUtil);
        }
        return ResultUtil.success(evaluationService.getSummaryEvaluation(user.getId(),courseId));
    }

    /**
     * 管理员获取教师总评价
     * <p>首先获取根据课程名称获取课程及课程对应的教师信息，根据获取的课程id和教师id获取评价，
     * 计算评价的加权平均结果，存入
     * <p>根据学生、教师、督导对于课程的评价计算加权平均值后返回教师总评价
     * <p>学生权重: 0.3
     * <p>教师权重: 0.3
     * <p>督导权重: 0.4
     * @param startPage 起始页
     * @param pageSize 单页容量
     * @param courseName 课程名
     * @return JSONObject
     */
    @GetMapping("/admin/summaryEvaluation")
    public Object findAllSummaryEvaluation(int startPage,int pageSize,String courseName) {
        Page p = (Page) evaluationService.getCoursesByAdmin(startPage,pageSize,courseName);
        JSONObject page =  new JSONObject();
        page.put("total",p.getTotal());
        page.put("size",p.getSize());
        page.put("current",p.getCurrent());
        page.put("pages",p.getPages());
        JSONArray records = (JSONArray) JSONArray.toJSON(p.getRecords());
        for(int j = 0;j < records.size();j++) {
            int courseId = ((JSONObject) records.get(j)).getInteger("id");
            int teacherId = ((JSONObject) records.get(j)).getJSONObject("teacher").getInteger("id");
            List<Map> summaryList = evaluationService.getSummaryEvaluation(teacherId,courseId);
            Double totalAvg = 0.0;
            //若其中一个角色未评价，则不返回
            int count = 0;
            for(int i=0;i < summaryList.size();i++) {
                switch ((String)((HashMap)summaryList.get(i)).get("role_name")) {
                    case "学生":
                        totalAvg += Double.parseDouble((String.valueOf(((HashMap)summaryList.get(i)).get("total_score"))) ) * 0.3;
                        count++;
                        break;
                    case "教师":
                        totalAvg += Double.parseDouble((String.valueOf(((HashMap)summaryList.get(i)).get("total_score"))) ) * 0.3;
                        count++;
                        break;
                    case "督导":
                        totalAvg += Double.parseDouble(String.valueOf( ((HashMap)summaryList.get(i)).get("total_score"))) * 0.4;
                        count++;
                        break;
                }
            }
            if(count >= 3) {
                ((JSONObject) records.get(j)).put("totalAvg",new DecimalFormat("#.00").format(totalAvg));
            }
        }
        page.put("records",records);
        return ResultUtil.success(JSONObject.parseObject(page.toJSONString()));
    }

    /**
     * 管理员通过教师id获取详细评价信息，教师id、课程id确定一组评价记录，组别依据评价者角色区分
     * @param teacherId 教师id
     * @param courseId 课程id
     * @return List<Map>
     */
    @GetMapping("/admin/summaryEvaluation/byTeacherId")
    public Object findSummaryEvaluationByTeacher(Integer teacherId,Integer courseId){
        return ResultUtil.success(evaluationService.getSummaryEvaluation(teacherId,courseId));
    }

    /**
     * 获取教师的所有课程列表
     * @param startPage 起始页
     * @param pageSize 单页容量
     * @param session 会话
     * @return Page<Course>
     */
    @GetMapping("/teacher/courseList")
    public Object findAllCourses(@NotNull Integer startPage,@NotNull Integer pageSize,HttpSession session) {
        User user = userService.getUserByName((String) session.getAttribute("username"));
        if(user == null) {
            ResultUtil resultUtil = ResultUtil.error(StatusCode.UNAUTHORIZED);
            throw new MyException(resultUtil);
        }
        return ResultUtil.success(evaluationService.getCoursesOfTeacher(user.getId(),startPage,pageSize));
    }

    /**
     * 获取教师自身的评价，由教师id、课程id、角色id唯一确定一条评价数据，获取评价的建议字段
     * @param courseId
     * @param roleId
     * @param startPage
     * @param pageSize
     * @param session
     * @return List<Map>
     */
    @GetMapping("/teacher/advices")
    public Object findAdvices(@NotNull Integer courseId, Integer roleId, Integer startPage, Integer pageSize, HttpSession session) {
        User user = userService.getUserByName((String) session.getAttribute("username"));
        if(user == null) {
            ResultUtil resultUtil = ResultUtil.error(StatusCode.UNAUTHORIZED);
            throw new MyException(resultUtil);
        }
        IPage p = evaluationService.getAdvices(user.getId(),courseId,roleId,startPage,pageSize);
        List newList = new ArrayList();
        List list = p.getRecords();
        for(Object advice:list) {
            Map<String,String> newMap = new HashMap<String,String>();
            newMap.put("adviceContent", (String) advice);
            newList.add(newMap);
        }
        p.setRecords(newList);
        return ResultUtil.success(p);
    }
}
