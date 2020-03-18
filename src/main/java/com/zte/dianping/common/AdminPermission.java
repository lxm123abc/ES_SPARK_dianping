package com.zte.dianping.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ProjectName: dianping-com.zte.dianping.common
 *
 * @Author: Liang Xiaomin
 * @Date: Creating in 16:54 2020/3/3
 * @Description:   权限切面
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AdminPermission {
    String productType() default "text/html";
}
