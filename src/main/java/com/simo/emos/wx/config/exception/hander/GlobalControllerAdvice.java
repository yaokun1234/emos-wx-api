package com.simo.emos.wx.config.exception.hander;

import com.simo.emos.wx.config.exception.ConditionException;
import com.simo.emos.wx.util.RespBean;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author simo
 * @Date 2023/2/23 17:04
 * @Version 1.0
 **/

@RestControllerAdvice
public class GlobalControllerAdvice {

    private static final String BAD_REQUEST_MSG = "客户端请求参数错误";
    // <1> 处理 form data方式调用接口校验失败抛出的异常
    @ExceptionHandler(BindException.class)
    public RespBean bindExceptionHandler(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<String> collect = fieldErrors.stream()
                .map(o -> o.getField()+":"+o.getDefaultMessage())
                .collect(Collectors.toList());
        return new RespBean(HttpStatus.BAD_REQUEST.value()+"", BAD_REQUEST_MSG, collect);
    }
    // <2> 处理 json 请求体调用接口校验失败抛出的异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RespBean methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<String> collect = fieldErrors.stream()
                .map(o -> o.getField()+":"+o.getDefaultMessage())
                .collect(Collectors.toList());
        return new RespBean(HttpStatus.BAD_REQUEST.value()+"", BAD_REQUEST_MSG, collect);
    }
    // <3> 处理单个参数校验失败抛出的异常
    @ExceptionHandler(ConstraintViolationException.class)
    public RespBean constraintViolationExceptionHandler(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        List<String> collect = constraintViolations.stream()
                .map(o -> o.getMessage())
                .collect(Collectors.toList());
        return new RespBean(HttpStatus.BAD_REQUEST.value()+"", BAD_REQUEST_MSG, collect);
    }

    @ExceptionHandler(ConditionException.class)
    public RespBean ConditionExceptionHandler(ConditionException e) {

        return RespBean.error(e.getMessage());
    }
}
