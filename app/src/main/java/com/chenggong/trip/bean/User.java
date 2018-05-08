package com.chenggong.trip.bean;

/**
 * Created by chenggong on 18-5-8.
 *
 * @author chenggong
 */

public class User {
    private String username;
    private String userId;

    public User(String username, String userId) {
        this.username = username;
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
