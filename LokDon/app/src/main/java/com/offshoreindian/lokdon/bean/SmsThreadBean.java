package com.offshoreindian.lokdon.bean;

import java.util.ArrayList;

/**
 * Created by praveshkumar on 16/10/16.
 */

public class SmsThreadBean
{
    private String senderName;
    private ArrayList<SmsBean> smsBeanArrayList;

    public SmsThreadBean()
    {
        smsBeanArrayList = new ArrayList<SmsBean>();
    }
    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public ArrayList<SmsBean> getSmsBeanArrayList() {
        return smsBeanArrayList;
    }

    public void setSmsBeanArrayList(ArrayList<SmsBean> smsBeanArrayList) {
        this.smsBeanArrayList = smsBeanArrayList;
    }
}
