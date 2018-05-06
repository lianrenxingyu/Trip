package com.chenggong.trip;

import android.app.Application;

import com.chenggong.trip.util.Logger;

/**
 * Created by chenggong on 18-5-6.
 *
 * @author chenggong
 */

public class MyApplication extends Application implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "MyApplication";
    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    //处理崩溃应用默认重启导致日志被冲洗掉的问题,延迟重启的时间
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (BuildConfig.DEBUG) {
            try {
                Logger.d(TAG, "因为为捕获异常线程休眠");
                e.printStackTrace();
                Thread.sleep(8000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
        mDefaultExceptionHandler.uncaughtException(t, e);
    }
}
