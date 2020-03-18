package com.zte.dianping.common;

import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ProjectName: dianping-com.zte.dianping.common
 *
 * @Author: Liang Xiaomin
 * @Date: Creating in 11:44 2020/2/20 0020
 * @Description:
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonResponse doErr(HttpServletRequest request, HttpServletResponse response,Exception ex){
        ex.printStackTrace();
        if (ex instanceof BusinessException){
            return CommonResponse.fail(((BusinessException) ex).getCommonError());
        }else if (ex instanceof NoHandlerFoundException){
            return CommonResponse.fail(new CommonError(EmBusinessError.BINDINGEXCEPTION_404));
        }else if (ex instanceof ServletRequestBindingException){
            return CommonResponse.fail(new CommonError(EmBusinessError.NO_HANDLER_FOUND));
        }else {
            return CommonResponse.fail(new CommonError(EmBusinessError.UNKNOWN_ERROR));
        }
    }



}
