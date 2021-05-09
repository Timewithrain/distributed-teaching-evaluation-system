package com.watermelon.api.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.watermelon.api.entity.EvaluationItem;

import java.util.List;

public interface EvaluationItemService {
    /**
     * 查询对应角色的评价列表
     * @param rid
     * @return
     */
    List<EvaluationItem> listEvaluationByRoleId(int rid);

    IPage<EvaluationItem> searchEvaluationByRoleId(int startPage, int pageSize, String roleId, String str);

    /**
     * 修改评价项
     * @param evaluationItem
     * @return
     */
    int updateEvaluationItem(EvaluationItem evaluationItem);

    /**
     * 插入评价项
     * @param evaluationItem
     * @return
     */
    int insertEvaluationItem(EvaluationItem evaluationItem);

    /**
     * 删除评价项
     * @param id
     * @return
     */
    int deleteEvaluationItem(int id);

}
