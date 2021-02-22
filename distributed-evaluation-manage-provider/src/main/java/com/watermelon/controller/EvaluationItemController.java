package com.watermelon.controller;

import com.watermelon.api.entity.EvaluationItem;
import com.watermelon.api.service.EvaluationItemService;
import com.watermelon.api.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/evaluationItem")
public class EvaluationItemController {

    @Autowired
    private EvaluationItemService evaluationItemService;

    @GetMapping("/list")
    public Object findList(int rid){
        return ResultUtil.success(evaluationItemService.findList(rid));
    }

    public Object findLists(){
        return ResultUtil.success(evaluationItemService.findLists());
    }

    @PutMapping("/update")
    public Object updateEvaluationItem(@RequestBody EvaluationItem evaluationItem){
        evaluationItemService.updateEvaluationItem(evaluationItem);
        return ResultUtil.success();
    }

    @DeleteMapping("/delete")
    public Object deleteEvaluationItem(int id){
        evaluationItemService.deleteEvaluationItem(id);
        return ResultUtil.success();
    }

    @PostMapping("/insert")
    public Object insertEvaluationItem(@RequestBody EvaluationItem evaluationItem){
        evaluationItemService.insertEvaluationItem(evaluationItem);
        return ResultUtil.success();
    }

}
