package com.zte.dianping.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.dianping.common.*;
import com.zte.dianping.model.SellerModel;
import com.zte.dianping.request.PageQuery;
import com.zte.dianping.request.SellerCreateReq;
import com.zte.dianping.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
@Controller("/admin/seller")
@RequestMapping("/admin/seller")
public class AdminSellerController {

    @Autowired
    private SellerService sellerService;

    //商户列表
    @RequestMapping("/index")
    @AdminPermission
    public ModelAndView index(PageQuery pageQuery){
        //PageHelper接入
        PageHelper.startPage(pageQuery.getPage(),pageQuery.getSize());
        List<SellerModel> sellerModels = sellerService.selectAll();
        //PageHelper封装
        PageInfo<SellerModel> sellerModelPageInfo = new PageInfo<>(sellerModels);
        ModelAndView modelAndView = new ModelAndView("/admin/seller/index");
        modelAndView.addObject("data",sellerModelPageInfo);
        modelAndView.addObject("CONTROLLER_NAME","seller");
        modelAndView.addObject("ACTION_NAME","index");
        return modelAndView;
    }

    @RequestMapping("/createpage")
    @AdminPermission
    public ModelAndView createpage(){
        ModelAndView modelAndView = new ModelAndView("/admin/seller/create");
        modelAndView.addObject("CONTROLLER_NAME","seller");
        modelAndView.addObject("ACTION_NAME","create");
        return modelAndView;
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @AdminPermission
    public String create(@Valid SellerCreateReq sellerCreateReq, BindingResult bindingResult) throws BusinessException {
        if (bindingResult.hasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, CommonUtils.processErrorString(bindingResult));
        }
        SellerModel sellerModel = new SellerModel();
        sellerModel.setName(sellerCreateReq.getName());
        sellerService.create(sellerModel);
        return "redirect:/admin/seller/index";
    }


    /**
     * 禁用
     * @param id
     * @return
     */
    @RequestMapping(value = "/down",method = RequestMethod.POST)
    @AdminPermission
    @ResponseBody
    public CommonResponse down(@RequestParam(value = "id")Integer id) throws BusinessException {
        SellerModel sellerModel = sellerService.changeStatus(id, 1);
        return CommonResponse.create(sellerModel);
    }
    @RequestMapping(value = "/up",method = RequestMethod.POST)
    @AdminPermission
    @ResponseBody
    public CommonResponse up(@RequestParam(value = "id")Integer id) throws BusinessException {
        SellerModel sellerModel = sellerService.changeStatus(id, 0);
        return CommonResponse.create(sellerModel);
    }

}
