package com.zte.dianping.service;

import com.zte.dianping.common.BusinessException;
import com.zte.dianping.model.CategoryModel;

import java.util.List;

/**
 * ProjectName: dianping-com.zte.dianping.service
 *
 * @Author: Liang Xiaomin
 * @Date: Creating in 11:33 2020/3/4
 * @Description:
 */
public interface CategoryService {

    CategoryModel create(CategoryModel categoryModel) throws BusinessException;

    CategoryModel get(Integer id);

    List<CategoryModel> selectAll();

    Integer countAllCategory();
}
