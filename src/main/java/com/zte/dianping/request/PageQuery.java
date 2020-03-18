package com.zte.dianping.request;

/**
 * ProjectName: dianping-com.zte.dianping.request
 *
 * @Author: Liang Xiaomin
 * @Date: Creating in 10:36 2020/3/4
 * @Description:
 */
public class PageQuery {

    private Integer page = 1;
    private Integer size = 10;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
