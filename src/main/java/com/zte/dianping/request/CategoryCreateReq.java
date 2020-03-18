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
public class CategoryCreateReq {

    @NotBlank(message = "类别名不能为空")
    private String name;
    @NotBlank(message = "iconUrl不能为空")
    private String iconUrl;
    @NotNull(message = "权重不能为空")
    private Integer sort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
