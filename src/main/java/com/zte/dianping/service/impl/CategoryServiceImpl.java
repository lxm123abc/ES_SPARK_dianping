package com.zte.dianping.service.impl;

import com.zte.dianping.common.BusinessException;
import com.zte.dianping.common.EmBusinessError;
import com.zte.dianping.dao.CategoryModelMapper;
import com.zte.dianping.model.CategoryModel;
import com.zte.dianping.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * ProjectName: dianping-com.zte.dianping.service.impl
 *
 * @Author: Liang Xiaomin
 * @Date: Creating in 11:33 2020/3/4
 * @Description:
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryModelMapper categoryModelMapper;

    @Override
    public CategoryModel create(CategoryModel categoryModel) throws BusinessException {
        categoryModel.setCreatedAt(new Date());
        categoryModel.setUpdatedAt(new Date());
        try {
            categoryModelMapper.insertSelective(categoryModel);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(EmBusinessError.CATEGORY_NAME_DUPLICATED);
        }
        return get(categoryModel.getId());
    }

    @Override
    public CategoryModel get(Integer id) {
        return categoryModelMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<CategoryModel> selectAll() {
        return categoryModelMapper.selectAll();
    }

    @Override
    public Integer countAllCategory() {
        return categoryModelMapper.countAllCategory();
    }
}
