package com.zte.dianping.common;

/**
 * ProjectName: dianping-com.zte.dianping.common
 *
 * @Author: Liang Xiaomin
 * @Date: Creating in 11:27 2020/2/20 0020
 * @Description:
 */
public class CommonError {

    private Integer errorCode;
    private String errorMsg;

    public CommonError(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
    public CommonError(EmBusinessError businessError) {
        this.errorCode = businessError.getErrCode();
        this.errorMsg = businessError.getErrMsg();
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
