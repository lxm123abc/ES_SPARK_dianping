package com.zte.dianping.controller.admin;

import com.zte.dianping.common.AdminPermission;
import com.zte.dianping.common.BusinessException;
import com.zte.dianping.common.CommonResponse;
import com.zte.dianping.common.EmBusinessError;
import com.zte.dianping.service.CategoryService;
import com.zte.dianping.service.SellerService;
import com.zte.dianping.service.ShopService;
import com.zte.dianping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ProjectName: dianping-com.zte.dianping.controller.admin
 *
 * @Author: Liang Xiaomin
 * @Date: Creating in 16:28 2020/3/3
 * @Description:
 */
@Controller("/admin/admin")
@RequestMapping("/admin/admin")
public class AdminController {

    @Value("${admin.email}")
    private String email;
    @Value("${admin.encryptPassword}")
    private String password;
    public static final String CURRENT_ADMIN_SESSION = "currentAdminSession";
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private UserService userService;
    @Autowired
    private SellerService sellerService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ShopService shopService;

    @RequestMapping("/index")
    @AdminPermission
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("admin/admin/index.html");
        modelAndView.addObject("userCount",userService.countAllUser());
        modelAndView.addObject("sellerCount",sellerService.countAllSeller());
        modelAndView.addObject("categoryCount",categoryService.countAllCategory());
        modelAndView.addObject("shopCount",shopService.countAllShop());
        modelAndView.addObject("CONTROLLER_NAME","admin");
        modelAndView.addObject("ACTION_NAME","index");
        return modelAndView;
    }

    @RequestMapping("/loginpage")
    public ModelAndView loginpage(){
        ModelAndView modelAndView = new ModelAndView("admin/admin/login");
        return modelAndView;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(@RequestParam(name = "email")String email,
                                @RequestParam(name = "password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户名密码不能为空");
        }

        if (email.equals(this.email) && encodeByMD5(password).equals(this.password)){
            //登录成功
            httpServletRequest.getSession().setAttribute(CURRENT_ADMIN_SESSION,email);
            return "redirect:/admin/admin/index";
        }else {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户名密码错误");
        }
    }
    private String encodeByMD5(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //MD5加密
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(messageDigest.digest(password.getBytes("utf-8")));
    }
}
