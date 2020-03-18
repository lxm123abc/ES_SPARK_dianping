package com.zte.dianping.service;

import com.zte.dianping.common.BusinessException;
import com.zte.dianping.model.UserModel;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * ProjectName: dianping-com.zte.dianping.service
 *
 * @Author: Liang Xiaomin
 * @Date: Creating in 16:17 2020/2/18 0018
 * @Description:
 */
public interface UserService {

    UserModel getUserById(Integer id);

    UserModel register(UserModel registerUser) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException;

    UserModel login(String telphone,String password) throws UnsupportedEncodingException, NoSuchAlgorithmException, BusinessException;

    Integer countAllUser();

}
