package com.watermelon.exception;

import com.watermelon.api.util.ResultUtil;
import com.watermelon.api.util.StatusCode;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    // 参数验证错误
    @ExceptionHandler
    public ResultUtil exceptionHandler(ConstraintViolationException e) {
        Map<Path,String> collect = e.getConstraintViolations().stream()
                .collect(Collectors.toMap(ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage));
        return ResultUtil.error(StatusCode.BAD_REQUEST,collect);
    }

    @ExceptionHandler
    public ResultUtil exceptionHandler(MethodArgumentNotValidException e) {
        Map<String,String> collect = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        return ResultUtil.error(StatusCode.BAD_REQUEST ,collect);
    }

    //数据库外键主键异常
    @ExceptionHandler
    public ResultUtil exceptionHandler(DataIntegrityViolationException e) {
        return ResultUtil.error(StatusCode.BAD_REQUEST,"违反数据库完整性");
    }

    //自定义的异常
    @ExceptionHandler
    public ResultUtil exceptionHandler(MyException e) {
        return e.resultUtil;
    }

    //其他异常
    @ExceptionHandler
    public ResultUtil exceptionHandler(Exception e) {
        return ResultUtil.error(StatusCode.EXCEPTION_ERROR);
    }

}
