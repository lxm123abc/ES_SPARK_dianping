package com.zte.dianping.common;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * ProjectName: dianping-com.zte.dianping.common
 *
 * @Author: Liang Xiaomin
 * @Date: Creating in 12:26 2020/2/25 0025
 * @Description:
 */
public class CommonUtils {

    public static String processErrorString(BindingResult bindingResult){
        if (!bindingResult.hasErrors()){
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (FieldError fieldError:bindingResult.getFieldErrors()){
            stringBuilder.append(fieldError.getDefaultMessage()+",");
        }
        return stringBuilder.substring(0,stringBuilder.length()-1);
    }

}
