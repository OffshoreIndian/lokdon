package com.offshoreindian.lokdon.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by praveshkumar on 15/10/16.
 */

public class SmsBean {

    private String _id;
    private String _address;
    private String _msg;
    private String _readState; //"0" for have not read sms and "1" for have read sms
    private String _time;
    private String _folderName;
    private String timeInDate;
    private int smsType = 1;// 1 means inbox and 2 means sent

    public String getId() {
        return _id;
    }

    public String getAddress() {
        return _address;
    }

    public String getMsg() {
        return _msg;
    }

    public String getReadState() {
        return _readState;
    }

    public String getTime() {
        return _time;
    }

    public String getFolderName() {
        return _folderName;
    }

    public void setId(String id) {
        _id = id;
    }

    public void setAddress(String address) {
        _address = address;
    }

    public void setMsg(String msg) {
        _msg = msg;
    }

    public void setReadState(String readState) {
        _readState = readState;
    }

    public void setTime(String time) {
        _time = time;
        setTimeInDate(convertTime(time));
    }

    public void setFolderName(String folderName) {
        _folderName = folderName;
    }

    public String getTimeInDate() {
        return timeInDate;
    }

    public void setTimeInDate(String timeInDate) {
        this.timeInDate = timeInDate;
    }

    private String convertTime(String time) {
        Date currentDate = new Date(Long.parseLong(time));
        DateFormat df = new SimpleDateFormat("dd:MM:yy HH:mm:ss");
        return df.format(currentDate);
    }

    public int getSmsType() {
        return smsType;
    }

    public void setSmsType(int smsType) {
        this.smsType = smsType;
    }

    @Override
    public String toString() {
        return "Sms{" +
                "_id='" + _id + '\'' +
                ", _address='" + _address + '\'' +
                ", _msg='" + _msg + '\'' +
                ", _readState='" + _readState + '\'' +
                ", _time='" + _time + '\'' +
                ", _folderName='" + _folderName + '\'' +
                '}';

    }
}
