package com.chenggong.trip;

import android.app.Application;
import android.content.Context;

import com.chenggong.trip.db.bean.MyObjectBox;
import com.chenggong.trip.util.Logger;
import com.squareup.leakcanary.LeakCanary;

import io.objectbox.BoxStore;

/**
 * Created by chenggong on 18-5-6.
 *
 * @author chenggong
 */

public class MyApplication extends Application implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "MyApplication";

    private static Context context;
    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;

    private static Application application;
    private BoxStore boxStore;

    @Override
    public void onCreate() {
        super.onCreate();

        if(LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
        LeakCanary.install(this);

        context = (MyApplication) getApplicationContext();
        mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        application = this;
        //数据库初始化
        boxStore = MyObjectBox.builder().androidContext(this).build();

    }

    public BoxStore getBoxStore() {
        return boxStore;
    }

    public static MyApplication getInstance() {
        return (MyApplication)application;
    }

    public static Context getGlobalContext() {
        return context;
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
