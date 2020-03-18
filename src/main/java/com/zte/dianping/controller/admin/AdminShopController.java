package com.zte.dianping.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.dianping.common.AdminPermission;
import com.zte.dianping.common.BusinessException;
import com.zte.dianping.common.CommonUtils;
import com.zte.dianping.common.EmBusinessError;
import com.zte.dianping.model.CategoryModel;
import com.zte.dianping.model.ShopModel;
import com.zte.dianping.request.CategoryCreateReq;
import com.zte.dianping.request.PageQuery;
import com.zte.dianping.request.ShopCreateReq;
import com.zte.dianping.service.CategoryService;
import com.zte.dianping.service.ShopService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

/**
 * ProjectName: dianping-com.zte.dianping.controller.admin
 *
 * @Author: Liang Xiaomin
 * @Date: Creating in 16:28 2020/3/3
 * @Description:
 */
@Controller("/admin/shop")
@RequestMapping("/admin/shop")
public class AdminShopController {

    @Autowired
    private ShopService shopService;

    //商户列表
    @RequestMapping("/index")
    @AdminPermission
    public ModelAndView index(PageQuery pageQuery){
        //PageHelper接入
        PageHelper.startPage(pageQuery.getPage(),pageQuery.getSize());
        List<ShopModel> shopModels = shopService.selectAll();
        //PageHelper封装
        PageInfo<ShopModel> categoryModelPageInfo = new PageInfo<>(shopModels);
        ModelAndView modelAndView = new ModelAndView("/admin/shop/index");
        modelAndView.addObject("data",categoryModelPageInfo);
        modelAndView.addObject("CONTROLLER_NAME","shop");
        modelAndView.addObject("ACTION_NAME","index");
        return modelAndView;
    }

    @RequestMapping("/createpage")
    @AdminPermission
    public ModelAndView createpage(){
        ModelAndView modelAndView = new ModelAndView("/admin/shop/create");
        modelAndView.addObject("CONTROLLER_NAME","shop");
        modelAndView.addObject("ACTION_NAME","create");
        return modelAndView;
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @AdminPermission
    public String create(@Valid ShopCreateReq shopCreateReq, BindingResult bindingResult) throws BusinessException {
        if (bindingResult.hasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, CommonUtils.processErrorString(bindingResult));
        }
        ShopModel shopModel = new ShopModel();
        BeanUtils.copyProperties(shopCreateReq,shopModel);
        shopService.create(shopModel);
        return "redirect:/admin/category/index";
    }



}
