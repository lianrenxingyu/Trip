package com.chenggong.trip.net;

/**
 * Created by chenggong on 18-5-10.
 *
 * @author chenggong
 *
 * 主要用于实时聊天的消息发送,接收的相关网络功能
 */

public class SocketUtil {
    private static final String TAG = "SocketUtil";

    public static void sendMsg(String userId,String name,SendMsgCallback callback){

    }

    public static void receiveMsg(ReceiveCallback callback){

    }
    public interface SendMsgCallback{
        void onResponse();
        void onFailure();
    }

    public interface ReceiveCallback{
        void onResponse();
        void onFailure();
    }
}
