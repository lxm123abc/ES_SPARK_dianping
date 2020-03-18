package com.zte.dianping.service;

import com.zte.dianping.common.BusinessException;
import com.zte.dianping.model.CategoryModel;
import com.zte.dianping.model.ShopModel;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * ProjectName: dianping-com.zte.dianping.service
 *
 * @Author: Liang Xiaomin
 * @Date: Creating in 11:27 2020/3/4
 * @Description:
 */
public interface ShopService {

    ShopModel create(ShopModel shopModel) throws BusinessException;

    ShopModel get(Integer id);

    List<ShopModel> selectAll();

    Integer countAllShop();

    List<ShopModel> recommend(BigDecimal longitude, BigDecimal latitude);

    Map<String, Object> searchES(BigDecimal longitude, BigDecimal latitude, String keyword, Integer orderby, Integer categoryId, String tags) throws IOException;

    List<ShopModel> search(BigDecimal longitude, BigDecimal latitude,
                           String keyword, Integer orderby,
                           Integer categoryId, String tags);

    List<Map<String, Object>> searchGroupByTags(String keyword, Integer categoryId, String tags);
}
