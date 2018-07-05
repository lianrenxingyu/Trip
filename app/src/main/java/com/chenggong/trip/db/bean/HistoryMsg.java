package com.chenggong.trip.db.bean;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by chenggong on 18-5-16.
 *
 * @author chenggong
 */

@Entity
public class HistoryMsg {

    @Id
    private long id;
    private String friendId;
    private String msg;
    private String date;
    private String time;

    public HistoryMsg() {
    }

    public HistoryMsg(FriendMsg friendMsg) {
        friendId = friendMsg.getFriendId();
        msg = friendMsg.getMsg();
        date = friendMsg.getDate();
        time = friendMsg.getTime();
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
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
