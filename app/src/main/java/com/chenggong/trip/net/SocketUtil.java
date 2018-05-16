package com.chenggong.trip.net;


import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chenggong.trip.util.Configure;
import com.chenggong.trip.util.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chenggong on 18-5-10.
 *
 * @author chenggong
 *         <p>
 *         主要用于实时聊天的消息发送,接收的相关网络功能
 */

public class SocketUtil {


    private static final String TAG = "SocketUtil";

    private static Socket client = null;

    private static DataOutputStream output = null;
    private static DataInputStream input = null;

    private static boolean quitFlag = true;//接收线程循环控制标志


    private SocketUtil() {
    }

    /**
     * 开始长连接,app开始的时候开启
     * 请调用{@link #endLongConnect()} 关闭资源
     * //todo不完善的设计
     * 如果socket网络连接失败,input,output,client都为空,会使得{@link #receiveMsg(ReceiveCallback)} {@link #sendMsg(String, String, SendMsgCallback)}出现空指针异常
     */
    public static void startLongConnect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client = new Socket("192.168.43.254", 8081);
                    //client.setSoTimeout(5000); //不设定阻塞时间,使得read一直阻塞
                    input = new DataInputStream(client.getInputStream());
                    output = new DataOutputStream(client.getOutputStream());
                    sendMsg("0", "0", null);
                    Logger.d(TAG, TAG + "初始化工作完成");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 关闭长连接,app结束的时候关闭
     */
    public static void endLongConnect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    quitFlag = false;
                    if (input != null) {
                        input.close();
                    }
                    if (output != null) {
                        output.close();
                    }
                    if (client != null) {
                        client.close();
                    }
                    Logger.d(TAG, TAG + "关闭完成");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public static void sendMsg(final String friendId, final String msg, @Nullable final SendMsgCallback callback) {
        int i = 0;
        if (output == null && i < 3) {
            startLongConnect();
            i++;
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                JSONObject object = new JSONObject();
                object.put("userId", Configure.localUser.getUserId());
                object.put("friendId", friendId);
                object.put("msg", msg);
                String jsonStr = JSON.toJSONString(object);
                try {
                    if (output == null) {
                        throw new ConnectException();
                    }
                    output.write(jsonStr.getBytes(Charset.forName("utf-8")));
                    Logger.d(TAG, "客户端发送数据");
                } catch (IOException e) {
                    e.printStackTrace();
                    if (callback != null) {
                        callback.onFailure(e);
                    }
                }
            }
        };
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(runnable);

    }

    /**
     * 这是一个循环读取输入流的方法,实现了长连接的效果
     *
     * @param callback
     */
    public static void receiveMsg(final ReceiveCallback callback) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Logger.d(TAG,"准备接收初始化数据");
                byte[] bytes = new byte[2048];//一个汉子两个字节,一个字母一个字节,大约能存储1024个汉子,2048个字母
                int len;
                quitFlag = true;//线程被启动,赋值为true
                while (quitFlag) {
                    Logger.d(TAG,"消息接收线程在循环");
                    try {
                        if (input == null) {
                            Thread.sleep(2000);//等待初始化工作
                            throw new ConnectException();
                        }
                        len = input.read(bytes);
                        if (len == -1) {
                            continue;
                        }
                        String msg = new String(bytes, 0, len);
                        callback.onResponse(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    /**
     * 发送消息回调接口,没有使用
     */
    public interface SendMsgCallback {
        void onFailure(Exception e);
    }

    /**
     * 接收服务器消息回调接口
     */
    public interface ReceiveCallback {
        void onResponse(String msg);

        void onFailure(Exception e);
    }
}
