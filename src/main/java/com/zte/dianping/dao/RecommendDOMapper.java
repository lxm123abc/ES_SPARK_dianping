package com.zte.dianping.dao;

import com.zte.dianping.model.RecommendDO;

public interface RecommendDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RecommendDO record);

    int insertSelective(RecommendDO record);

    RecommendDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RecommendDO record);

    int updateByPrimaryKey(RecommendDO record);
}