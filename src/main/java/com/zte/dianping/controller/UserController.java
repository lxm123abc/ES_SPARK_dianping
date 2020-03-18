package com.zte.dianping.controller;

import com.zte.dianping.common.*;
import com.zte.dianping.model.UserModel;
import com.zte.dianping.request.LoginReq;
import com.zte.dianping.request.RegisterReq;
import com.zte.dianping.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * ProjectName: dianping-com.zte.dianping.controller
 *
 * @Author: Liang Xiaomin
 * @Date: Creating in 16:17 2020/2/18 0018
 * @Description:
 */
@Controller("/user")
@RequestMapping("/user")
public class UserController {


    public static final String CURRENT_USER_SESSION = "currentUserSession";

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    @ResponseBody
    public CommonResponse test(@RequestParam(name = "id")Integer id) throws BusinessException {
        UserModel userModel = userService.getUserById(id);
        if (userModel == null){
            /*return CommonResponse.create(
                    new CommonError(EmBusinessError.NO_OBJECT_FOUND),"fail");*/
            throw new BusinessException(EmBusinessError.NO_OBJECT_FOUND);
        }
        return CommonResponse.create(userModel);
    }

    @RequestMapping("/")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("/index");
        modelAndView.addObject("name","lxm");
        return modelAndView;
    }

    @RequestMapping("/register")
    @ResponseBody
    public CommonResponse register(@Valid @RequestBody RegisterReq registerReq, BindingResult bindingResult) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (bindingResult.hasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, CommonUtils.processErrorString(bindingResult));
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(registerReq,userModel);
        UserModel register = userService.register(userModel);
        return CommonResponse.create(register);
    }

    @RequestMapping("/login")
    @ResponseBody
    public CommonResponse login(@Valid @RequestBody LoginReq loginReq, BindingResult bindingResult) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (bindingResult.hasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, CommonUtils.processErrorString(bindingResult));
        }
        UserModel login = userService.login(loginReq.getTelphone(), loginReq.getPassword());
        httpServletRequest.getSession().setAttribute(CURRENT_USER_SESSION,login);

        return CommonResponse.create(login);
    }


    @RequestMapping("/logout")
    @ResponseBody
    public CommonResponse logout() {
        httpServletRequest.getSession().invalidate();
        return CommonResponse.create(null);
    }

    @PostMapping("/getcurrentuser")
    @ResponseBody
    public CommonResponse getCurrentUser(){
        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute(CURRENT_USER_SESSION);
        return CommonResponse.create(userModel);
    }

}
