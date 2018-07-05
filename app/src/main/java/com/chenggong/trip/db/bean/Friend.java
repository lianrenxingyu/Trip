package com.chenggong.trip.db.bean;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by chenggong on 18-5-16.
 *
 * @author chenggong
 */
@Entity
public class Friend {

    @Id
    private long id;
    private String friendName;
    private String friendId;

    public Friend() {
    }

    public Friend(String friendName, String friendId) {
        this.friendName = friendName;
        this.friendId = friendId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }
}
