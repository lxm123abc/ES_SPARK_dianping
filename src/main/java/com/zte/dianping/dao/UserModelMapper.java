package com.zte.dianping.dao;

import com.zte.dianping.model.UserModel;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

public interface UserModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserModel record);

    int insertSelective(UserModel record);

    UserModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserModel record);

    int updateByPrimaryKey(UserModel record);

    UserModel selectByPhoneAndPassword(@Param("telphone")String telphone,@Param("password")String password);

    Integer countAllUser();
}