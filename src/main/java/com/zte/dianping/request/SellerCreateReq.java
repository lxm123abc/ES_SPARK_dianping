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
public class SellerCreateReq {

    @NotBlank(message = "商户名不能为空")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
