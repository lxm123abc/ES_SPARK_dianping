package com.zte.dianping.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.dianping.common.*;
import com.zte.dianping.model.CategoryModel;
import com.zte.dianping.model.SellerModel;
import com.zte.dianping.request.CategoryCreateReq;
import com.zte.dianping.request.PageQuery;
import com.zte.dianping.request.SellerCreateReq;
import com.zte.dianping.service.CategoryService;
import com.zte.dianping.service.SellerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
@Controller("/admin/category")
@RequestMapping("/admin/category")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    //商户列表
    @RequestMapping("/index")
    @AdminPermission
    public ModelAndView index(PageQuery pageQuery){
        //PageHelper接入
        PageHelper.startPage(pageQuery.getPage(),pageQuery.getSize());
        List<CategoryModel> categoryModels = categoryService.selectAll();
        //PageHelper封装
        PageInfo<CategoryModel> categoryModelPageInfo = new PageInfo<>(categoryModels);
        ModelAndView modelAndView = new ModelAndView("/admin/category/index");
        modelAndView.addObject("data",categoryModelPageInfo);
        modelAndView.addObject("CONTROLLER_NAME","category");
        modelAndView.addObject("ACTION_NAME","index");
        return modelAndView;
    }

    @RequestMapping("/createpage")
    @AdminPermission
    public ModelAndView createpage(){
        ModelAndView modelAndView = new ModelAndView("/admin/category/create");
        modelAndView.addObject("CONTROLLER_NAME","category");
        modelAndView.addObject("ACTION_NAME","create");
        return modelAndView;
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @AdminPermission
    public String create(@Valid CategoryCreateReq categoryCreateReq, BindingResult bindingResult) throws BusinessException {
        if (bindingResult.hasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, CommonUtils.processErrorString(bindingResult));
        }
        CategoryModel categoryModel = new CategoryModel();
        BeanUtils.copyProperties(categoryCreateReq,categoryModel);
        categoryService.create(categoryModel);
        return "redirect:/admin/category/index";
    }

}
