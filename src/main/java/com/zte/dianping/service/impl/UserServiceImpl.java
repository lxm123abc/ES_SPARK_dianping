package com.zte.dianping.service.impl;

import com.zte.dianping.common.BusinessException;
import com.zte.dianping.common.EmBusinessError;
import com.zte.dianping.dao.UserModelMapper;
import com.zte.dianping.model.UserModel;
import com.zte.dianping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * ProjectName: dianping-com.zte.dianping.service.impl
 *
 * @Author: Liang Xiaomin
 * @Date: Creating in 16:30 2020/2/18 0018
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserModelMapper userModelMapper;

    @Override
    public UserModel getUserById(Integer id) {
        return userModelMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public UserModel register(UserModel registerUser) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        registerUser.setCreatedDt(new Date());
        registerUser.setUpdatedDt(new Date());

        registerUser.setPassword(encodeByMD5(registerUser.getPassword()));
        try {
            userModelMapper.insertSelective(registerUser);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(EmBusinessError.REGISTER_DUP_FAIL);
        }
        return getUserById(registerUser.getId());
    }

    @Override
    public UserModel login(String telphone, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException, BusinessException {
        UserModel userModel = userModelMapper.selectByPhoneAndPassword(telphone, encodeByMD5(password));
        if (userModel == null){
            throw new BusinessException(EmBusinessError.LOGIN_FAIL);
        }
        return userModel;
    }

    @Override
    public Integer countAllUser() {
        return userModelMapper.countAllUser();
    }

    private String encodeByMD5(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //MD5加密
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(messageDigest.digest(password.getBytes("utf-8")));
    }
}
