package com.zte.dianping.service.impl;

import com.zte.dianping.common.BusinessException;
import com.zte.dianping.common.EmBusinessError;
import com.zte.dianping.dao.SellerModelMapper;
import com.zte.dianping.dao.UserModelMapper;
import com.zte.dianping.model.SellerModel;
import com.zte.dianping.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * ProjectName: dianping-com.zte.dianping.service.impl
 *
 * @Author: Liang Xiaomin
 * @Date: Creating in 9:53 2020/3/4
 * @Description:
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerModelMapper sellerModelMapper;
    @Autowired
    private UserModelMapper userModelMapper;

    @Override
    @Transactional
    public SellerModel create(SellerModel sellerModel) {
        sellerModel.setCreatedAt(new Date());
        sellerModel.setUpdatedAt(new Date());
        sellerModel.setRemarkScore(new BigDecimal(0));
        sellerModel.setDisabledFlag(0);
        sellerModelMapper.insertSelective(sellerModel);
        return get(sellerModel.getId());
    }

    @Override
    public SellerModel get(Integer id) {
        return sellerModelMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SellerModel> selectAll() {
        return sellerModelMapper.selectAll();
    }

    @Override
    public SellerModel changeStatus(Integer id, Integer disabledFlag) throws BusinessException {
        SellerModel sellerModel = get(id);
        if (sellerModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        sellerModel.setDisabledFlag(disabledFlag);
        sellerModelMapper.updateByPrimaryKeySelective(sellerModel);
        return sellerModel;
    }

    @Override
    public Integer countAllSeller() {
        return sellerModelMapper.countAllSeller();
    }
}