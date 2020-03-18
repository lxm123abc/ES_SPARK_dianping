package com.zte.dianping.request;

import com.zte.dianping.model.CategoryModel;
import com.zte.dianping.model.SellerModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * ProjectName: dianping-com.zte.dianping.request
 *
 * @Author: Liang Xiaomin
 * @Date: Creating in 12:10 2020/2/25 0025
 * @Description:
 */
public class ShopCreateReq {

    @NotBlank(message = "服务名不能为空")
    private String name;

    @NotNull(message = "人均不能为空")
    private Integer pricePerMan;

    @NotNull(message = "纬度不能为空")
    private BigDecimal latitude;

    @NotNull(message = "精度不能为空")
    private BigDecimal longitude;

    @NotBlank(message = "类别不能为空")
    private Integer categoryId;

    private String tags;

    @NotBlank(message = "营业开始时间不能为空")
    private String startTime;

    @NotBlank(message = "营业结束时间不能为空")
    private String endTime;

    @NotBlank(message = "营业地址不能为空")
    private String address;

    @NotNull(message = "商家id 不能为空")
    private Integer sellerId;

    @NotBlank(message = "图标不能为空")
    private String iconUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPricePerMan() {
        return pricePerMan;
    }

    public void setPricePerMan(Integer pricePerMan) {
        this.pricePerMan = pricePerMan;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
