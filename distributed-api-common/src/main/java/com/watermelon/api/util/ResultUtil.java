package com.watermelon.api.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

//封装返回结果
@JsonInclude(JsonInclude.Include.NON_NULL)  //非空才会序列化
@Data
public class ResultUtil {
    private Integer code;
    private String msg;
    private Object data;
    private Object info;
    /**
     * 返回一个info为空对象的成功消息的json
     */
    public static ResultUtil success() {
        ResultUtil resultUtil = new ResultUtil();
        resultUtil.setCode(StatusCode.OK.getValue());
        resultUtil.setMsg(StatusCode.OK.getText());
        return resultUtil;
    }

    /**
     * 返回一个返回码为200的成功消息的
     */
    public static ResultUtil success(Object data) {
        ResultUtil resultUtil = new ResultUtil();
        resultUtil.setCode(StatusCode.OK.getValue());
        resultUtil.setMsg(StatusCode.OK.getText());
        resultUtil.setData(data);
        return resultUtil;
    }

    /**
     * 返回错误信息
     */
    public static ResultUtil error(StatusCode errorEnum) {
        ResultUtil resultUtil = new ResultUtil();
        resultUtil.setCode(errorEnum.getValue());
        resultUtil.setMsg(errorEnum.getText());
        return resultUtil;
    }

    public static ResultUtil error(StatusCode errorEnum,Object info) {
        ResultUtil resultUtil = new ResultUtil();
        resultUtil.setCode(errorEnum.getValue());
        resultUtil.setMsg(errorEnum.getText());
        resultUtil.setInfo(info);
        return resultUtil;
    }
}
