package com.chenggong.trip.net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by chenggong on 18-5-5.
 *
 * @author chenggong
 */

public class HttpUtil {

    /**
     * 发送表单数据formbody数据,单独拿出一个方法好坏有待考虑
     *
     * @param map 表单数据
     */
    public static void sendFormRequest(String url, Map<String, String> map, Callback callback) {
        OkHttpClient client = new OkHttpClient();
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
