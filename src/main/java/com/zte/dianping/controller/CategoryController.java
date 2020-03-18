package com.zte.dianping.controller;

import com.zte.dianping.common.CommonResponse;
import com.zte.dianping.model.CategoryModel;
import com.zte.dianping.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * ProjectName: dianping-com.zte.dianping.controller
 *
 * @Author: Liang Xiaomin
 * @Date: Creating in 19:25 2020/3/4
 * @Description:
 */
@Controller("/category")
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @RequestMapping("/list")
    @ResponseBody
    public CommonResponse list(){
        List<CategoryModel> categoryModels = categoryService.selectAll();
        return CommonResponse.create(categoryModels);
    }

}
