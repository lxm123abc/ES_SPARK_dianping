package com.zte.dianping.controller;

import com.zte.dianping.common.BusinessException;
import com.zte.dianping.common.CommonResponse;
import com.zte.dianping.common.EmBusinessError;
import com.zte.dianping.model.CategoryModel;
import com.zte.dianping.model.ShopModel;
import com.zte.dianping.service.CategoryService;
import com.zte.dianping.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("/shop")
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private CategoryService categoryService;

    //推荐服务V1.0   经纬度参数
    @RequestMapping("/recommend")
    @ResponseBody
    public CommonResponse recommend(@RequestParam(name="longitude")BigDecimal longitude,
                               @RequestParam(name="latitude")BigDecimal latitude) throws BusinessException {
        if(longitude == null || latitude == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        List<ShopModel> shopModelList = shopService.recommend(longitude,latitude);
        return CommonResponse.create(shopModelList);
    }


    /**
     * 搜索服务V1.0
     * @param longitude 精度
     * @param latitude 纬度
     * @param keyword  用户搜索关键字
     * @param orderby  排序
     * @param categoryId 类别
     * @param tags  商家标签
     * @return
     * @throws BusinessException
     * @throws IOException
     */
    @RequestMapping("/search")
    @ResponseBody
    public CommonResponse search(@RequestParam(name="longitude")BigDecimal longitude,
                            @RequestParam(name="latitude")BigDecimal latitude,
                            @RequestParam(name="keyword")String keyword,
                            @RequestParam(name="orderby",required = false)Integer orderby,
                            @RequestParam(name="categoryId",required = false)Integer categoryId,
                            @RequestParam(name="tags",required = false)String tags) throws BusinessException, IOException {
        if(StringUtils.isEmpty(keyword) || longitude == null || latitude == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //1.0 数据库search
        //List<ShopModel> list = shopService.search(longitude,latitude,keyword,orderby,categoryId,tags);
        //2.0 ES+数据库
        Map<String,Object> result = shopService.searchES(longitude,latitude,keyword,orderby,categoryId,tags);
        List<ShopModel> shopModelList = (List<ShopModel>) result.get("shop");
        List<CategoryModel> categoryModelList = categoryService.selectAll();
        //List<Map<String,Object>> tagsAggregation = shopService.searchGroupByTags(keyword,categoryId,tags);  1.0
        List<Map<String,Object>>  tagsAggregation = (List<Map<String, Object>>) result.get("tags");
        Map<String,Object> resMap = new HashMap<>();
        resMap.put("shop",shopModelList);
        resMap.put("category",categoryModelList);
        resMap.put("tags",tagsAggregation);
        return CommonResponse.create(resMap);

    }




}
