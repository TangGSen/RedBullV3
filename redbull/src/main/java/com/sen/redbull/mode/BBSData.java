package com.sen.redbull.mode;

import java.util.List;

/**
 * Created by Administrator on 2016/4/6.
 */
public class BBSData {
    private List<BbsListBean> bbsListBean;
    private List<BbsBean> bbsDate;

    public BBSData (List<BbsListBean> bbsListBean,List<BbsBean> bbsDate){
        this.bbsListBean =bbsListBean;
        this.bbsDate = bbsDate ;
    }

    public List<BbsListBean> getBbsListBean() {
        return bbsListBean;
    }

    public void setBbsListBean(List<BbsListBean> bbsListBean) {
        this.bbsListBean = bbsListBean;
    }

    public List<BbsBean> getBbsDate() {
        return bbsDate;
    }

    public void setBbsDate(List<BbsBean> bbsDate) {
        this.bbsDate = bbsDate;
    }
}
