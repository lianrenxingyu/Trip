package com.chenggong.trip.util;

import com.chenggong.trip.bean.User;

/**
 * Created by chenggong on 18-5-1.
 * 配置类
 *
 * @author chenggong
 */

public class Configure {

    private Configure(){};
    public final static String NEWS = "news";
    public final static String CONTACTS = "contacts";
    public final static String NEAR = "near";

    public static String token;

    //登录状态码
    public final static String LoginFail= "0";
    public final static String LoginSuccess= "1";

    //用户
    public static User localUser;

}
