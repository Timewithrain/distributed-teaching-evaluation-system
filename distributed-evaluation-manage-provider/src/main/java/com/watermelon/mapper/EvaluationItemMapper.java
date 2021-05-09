package com.watermelon.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.watermelon.api.entity.EvaluationItem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EvaluationItemMapper {

    EvaluationItem getEvaluationItemById(int id);

    List<EvaluationItem> listEvaluationByRoleId(int roleId);

    IPage<EvaluationItem> searchEvaluationItem(Page page, String roleId, String str);

    int updateEvaluationItem(EvaluationItem item);

    int deleteEvaluationItem(int id);

    int insertEvaluationItem(EvaluationItem item);

}
