package com.zte.dianping.common;

import com.zte.dianping.controller.admin.AdminController;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * ProjectName: dianping-com.zte.dianping.common
 *
 * @Author: Liang Xiaomin
 * @Date: Creating in 16:56 2020/3/3
 * @Description:
 */
@Aspect
@Configuration
public class ControllerAspect {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    /**
     * 切面拦截
     *
     * 固定包下  &&  有某注解
     * @param proceedingJoinPoint
     * @return
     */
    @Around("execution(* com.zte.dianping.controller.admin.*.*(..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object adminControllerBeforeVaild(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Method method = ((MethodSignature)proceedingJoinPoint.getSignature()).getMethod();
        AdminPermission annotation = method.getAnnotation(AdminPermission.class);
        if (annotation == null){
            //没有注解放行，属于公共方法
            Object proceed = proceedingJoinPoint.proceed();//执行方法拿到返回值
            return proceed;
        }
        //如标注AdminPermission注解必须登录访问接口
        Object email = request.getSession().getAttribute(AdminController.CURRENT_ADMIN_SESSION);
        if (email == null){
            //未登录
            if (annotation.productType().equals("text/html")){
                response.sendRedirect("/admin/admin/loginpage");
                return null;
            }else {
                CommonError commonError = new CommonError(EmBusinessError.ADMIN_SHOULD_LOGIN);
                return CommonResponse.fail(commonError);
            }

        }else {
            Object proceed = proceedingJoinPoint.proceed();
            return proceed;
        }
    }

}
