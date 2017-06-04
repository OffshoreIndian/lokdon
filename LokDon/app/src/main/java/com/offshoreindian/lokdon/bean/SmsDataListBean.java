package com.offshoreindian.lokdon.bean;

import java.util.ArrayList;

/**
 * Created by praveshkumar on 16/10/16.
 */

public class SmsDataListBean {
    ArrayList<SmsBean> smsBeanArrayList;

    public SmsDataListBean(ArrayList<SmsBean> smsBeanArrayList) {
        this.smsBeanArrayList = smsBeanArrayList;
    }

    public ArrayList<SmsBean> getSmsBeanArrayList() {
        return smsBeanArrayList;
    }

    public void setSmsBeanArrayList(ArrayList<SmsBean> smsBeanArrayList) {
        this.smsBeanArrayList = smsBeanArrayList;
    }
    public void destroy()
    {
        smsBeanArrayList.clear();
        smsBeanArrayList = null;
    }
}
