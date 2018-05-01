package com.chenggong.trip.bean;

/**
 * Created by chenggong on 18-5-1.
 *
 * @author chenggong
 */

public class News {
    private String name;
    private String brief_msg;
    private String time;
    private String imagePath;

    public News(){}
    public News(String name, String brief_msg, String time, String imagePath) {
        this.name = name;
        this.brief_msg = brief_msg;
        this.time = time;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrief_msg() {
        return brief_msg;
    }

    public void setBrief_msg(String brief_msg) {
        this.brief_msg = brief_msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "name: "+name+" brief_msg: "+brief_msg+" time: "+time+" imagePath: "+imagePath;
    }
}
