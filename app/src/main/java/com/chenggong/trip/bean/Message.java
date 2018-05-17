package com.chenggong.trip.bean;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by chenggong on 18-5-11.
 *
 * @author chenggong
 */

public class Message {
    private String friendName;
    private String msg;
    private String type;
    private String time;

    //todo 日期问题处理
    private String date;

    public Message(String friendName, String msg, String type) {
        this.friendName = friendName;
        this.msg = msg;
        this.type = type;
        long time = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int minute = calendar.get(Calendar.MINUTE);
        int hour = calendar.get(Calendar.HOUR);
        this.time = String.valueOf(hour) + ":" + String.valueOf(minute);
    }

    public Message(String friendName, String msg, String type, String time, String date) {
        this.friendName = friendName;
        this.msg = msg;
        this.type = type;
        this.time = time;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
