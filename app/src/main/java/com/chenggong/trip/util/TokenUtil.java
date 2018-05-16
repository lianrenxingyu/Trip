package com.chenggong.trip.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.chenggong.trip.MyApplication;
import com.chenggong.trip.R;

import java.util.Date;

/**
 * Created by chenggong on 18-5-7.
 *
 * @author chenggong
 */

//todo token过期功能实现

public class TokenUtil {
    private static final String TAG = "TokenUtil";
    /**
     * 本地保存token
     */
    public static void saveToken(String token) {
        SharedPreferences sp = MyApplication.getGlobalContext().getSharedPreferences("tripPreferenceFile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        //清除原有的token
        if (sp.contains("token")) {
            editor.remove("token");
        }
        editor.putString("token", token);
        editor.commit();
        Logger.d(TAG,"token 被保存");
    }

    /**
     * 设置token日期
     */
    public static void setDate() {

    }

    /**
     * @return 返回当前token的日期
     */
    public static Date getDate() {

        return null;
    }
}
