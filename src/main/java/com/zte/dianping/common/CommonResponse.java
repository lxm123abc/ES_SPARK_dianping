package com.zte.dianping.common;

/**
 * ProjectName: dianping-com.zte.dianping.common
 *
 * @Author: Liang Xiaomin
 * @Date: Creating in 11:23 2020/2/20 0020
 * @Description:
 */
public class CommonResponse {

    // success fail
    private String status;
    // 数据
    private Object data;

    public static CommonResponse create(Object obj){
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setStatus("success");
        commonResponse.setData(obj);
        return commonResponse;
    }

    public static CommonResponse create(Object obj,String status){
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setStatus(status);
        commonResponse.setData(obj);
        return commonResponse;
    }
    public static CommonResponse fail(Object obj){
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setStatus("fail");
        commonResponse.setData(obj);
        return commonResponse;
    }







    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    private CommonResponse() {
    }
}
