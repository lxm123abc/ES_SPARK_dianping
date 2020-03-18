package com.zte.dianping.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * ProjectName: dianping-com.zte.dianping.request
 *
 * @Author: Liang Xiaomin
 * @Date: Creating in 12:10 2020/2/25 0025
 * @Description:
 */
public class LoginReq {

    @NotBlank(message = "手机号不能为空")
    private String telphone;
    @NotBlank(message = "密码不能为空")
    private String password;

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
