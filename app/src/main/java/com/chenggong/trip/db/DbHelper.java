package com.chenggong.trip.db;

import com.chenggong.trip.MyApplication;
import com.chenggong.trip.db.bean.Friend;
import com.chenggong.trip.db.bean.FriendMsg;
import com.chenggong.trip.db.bean.Friend_;
import com.chenggong.trip.util.Logger;
import com.chenggong.trip.util.StringUtil;

import java.util.List;

import io.objectbox.Box;
import okhttp3.internal.Util;

/**
 * Created by chenggong on 18-5-16.
 *
 * @author chenggong
 */

public class DbHelper {

    private static final String TAG = "DbHelper";

    /**
     * 获取用户名字
     * @param friendId
     * @return
     */
    public static String getFriendName(String friendId) {
        Box<Friend> box = MyApplication.getInstance().getBoxStore().boxFor(Friend.class);
        List<Friend> friendList = box.query().equal(Friend_.friendId, friendId).build().find();
        String name = friendList.get(0).getFriendName();
        Logger.d(TAG, "朋友名字 : " + name);
        return name;
    }

    /**
     * 向数据库添加朋友
     * @param name
     */
    public static void addFriend(String name) {
        Friend friend = new Friend(name, StringUtil.md5UserId(name));
        addFriend(friend);
    }

    public static void addFriend(Friend friend) {
        Box<Friend> box = MyApplication.getInstance().getBoxStore().boxFor(Friend.class);
        box.put(friend);
    }

    public static void addFriendMsg(FriendMsg msg){
        Box<FriendMsg> box = MyApplication.getInstance().getBoxStore().boxFor(FriendMsg.class);
        box.put(msg);
    }

    public static void clearAllData(){
        Box<Friend> box = MyApplication.getInstance().getBoxStore().boxFor(Friend.class);
        box.removeAll();
        Box<FriendMsg> msgBox = MyApplication.getInstance().getBoxStore().boxFor(FriendMsg.class);
        msgBox.removeAll();
        Logger.d(TAG,"数据库中数据全部删除");
    }
}
