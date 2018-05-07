package com.chenggong.trip.net;

import com.chenggong.trip.util.Configure;
import com.chenggong.trip.util.Logger;

import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by chenggong on 18-5-5.
 *
 * @author chenggong
 */

public class HttpUtil {

    private static final String TAG = "HttpUtil";
    /**
     * 发送表单数据formbody数据,单独拿出一个方法好坏有待考虑
     *
     * @param map 表单数据
     */
    public static void sendFormRequest(String url, Map<String, String> map, Callback callback) {

        //okhttp的cookie自动管理工具
        CookieJar cookieJar = new CookieJar() {
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                for (Cookie cookie:cookies){
                    Logger.d(TAG,cookie.value());
                    Logger.d(TAG,url.toString());
                    if (cookie.name().equals("token")){
                        Configure.token = cookie.value();
                        Logger.d(TAG,"服务器返回的token值:"+cookie.value());
                    }
                }
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                return Collections.emptyList();
            }
        };

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)   //10秒超时
                .cookieJar(cookieJar)
                .build();

//        MediaType mediaType = MediaType.parse("text/plain");
        FormBody.Builder builder = new FormBody.Builder();
        try {
            for (String key : map.keySet()) {
                String value = URLEncoder.encode(map.get(key), "utf-8");
                key = URLEncoder.encode(key, "utf-8");
                builder.add(key, value);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        FormBody formBody = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("content-type", "text/plain;charset=utf-8")
                .post(formBody)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
