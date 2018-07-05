package com.chenggong.trip.bean;

/**
 * Created by chenggong on 18-5-1.
 *
 * @author chenggong
 */

public class Contact {

    private String name;
    private String nickName;
    private String imagePath;

    public Contact(String name, String nickName, String imagePath) {
        this.name = name;
        this.nickName = nickName;
        this.imagePath = imagePath;
    }

    public Contact(String name) {
        this.name = name;
    }

    public Contact() {
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
