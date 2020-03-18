package com.zte.dianping.service;

import com.zte.dianping.common.BusinessException;
import com.zte.dianping.model.SellerModel;

import java.util.List;

/**
 * ProjectName: dianping-com.zte.dianping.service
 *
 * @Author: Liang Xiaomin
 * @Date: Creating in 9:53 2020/3/4
 * @Description:
 */
public interface SellerService {

    SellerModel create(SellerModel sellerModel);

    SellerModel get(Integer id);

    List<SellerModel> selectAll();

    SellerModel changeStatus(Integer id,Integer disabledFlag) throws BusinessException;

    Integer countAllSeller();
}
