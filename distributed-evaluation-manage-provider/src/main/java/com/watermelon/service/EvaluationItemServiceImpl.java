package com.watermelon.service;

import com.alibaba.fastjson.JSONObject;
import com.watermelon.api.entity.EvaluationItem;
import com.watermelon.api.service.EvaluationItemService;
import com.watermelon.mapper.EvaluationItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationItemServiceImpl implements EvaluationItemService {

    @Autowired
    private EvaluationItemMapper evaluationItemMapper;

    @Override
    public List<EvaluationItem> findList(int rid) {
        return evaluationItemMapper.getEvaluationsByRoleId(rid);
    }

    @Override
    public int updateEvaluationItem(EvaluationItem evaluationItem) {
        return evaluationItemMapper.updateEvaluationItem(evaluationItem);
    }

    @Override
    public int insertEvaluationItem(EvaluationItem evaluationItem) {
        return evaluationItemMapper.insertEvaluationItem(evaluationItem);
    }

    @Override
    public int deleteEvaluationItem(int id) {
        return evaluationItemMapper.deleteEvaluationItem(id);
    }

    @Override
    public JSONObject findLists() {
        return null;
    }

}
