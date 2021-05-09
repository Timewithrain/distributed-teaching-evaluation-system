package com.watermelon.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.watermelon.api.entity.EvaluationItem;
import com.watermelon.api.service.EvaluationItemService;
import com.watermelon.api.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/evaluationItem")
public class EvaluationItemController {

    @Autowired
    private EvaluationItemService evaluationItemService;

    @GetMapping("/listItem")
    public ResultUtil listEvaluationItem(int roleId){
        return ResultUtil.success(evaluationItemService.listEvaluationByRoleId(roleId));
    }

    @GetMapping("/searchItem")
    public ResultUtil searchEvaluationItem(int startPage, int pageSize, String roleId, String str){
        IPage<EvaluationItem> list = evaluationItemService.searchEvaluationByRoleId(startPage,pageSize,roleId,str);
        Map<String,Object> map = new HashMap<>();
        map.put("list",list.getRecords());
        map.put("total",list.getTotal());
        return ResultUtil.success(map);
    }

    @PutMapping("/updateItem")
    public ResultUtil updateEvaluationItem(@RequestBody EvaluationItem evaluationItem){
        evaluationItemService.updateEvaluationItem(evaluationItem);
        return ResultUtil.success();
    }

    @DeleteMapping("/deleteItem")
    public ResultUtil deleteEvaluationItem(int id){
        evaluationItemService.deleteEvaluationItem(id);
        return ResultUtil.success();
    }

    @PostMapping("/addItem")
    public ResultUtil addEvaluationItem(@RequestBody EvaluationItem evaluationItem){
        evaluationItemService.insertEvaluationItem(evaluationItem);
        return ResultUtil.success();
    }

}
