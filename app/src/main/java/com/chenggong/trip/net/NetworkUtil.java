package com.chenggong.trip.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

/**
 * Created by chenggong on 18-5-10.
 *
 * @author chenggong
 */

public class NetworkUtil {
    public static boolean isConnected(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        //todo 为了便于测试,这里都返回了true
        if(activeNetwork == null) return true;
        return true;
    }
}
