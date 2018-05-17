package com.chenggong.trip.db;

import com.chenggong.trip.MyApplication;
import com.chenggong.trip.db.bean.Friend;
import com.chenggong.trip.db.bean.FriendMsg;
import com.chenggong.trip.db.bean.FriendMsg_;
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
     *
     * @param friendId
     * @return
     */
    public static String getFriendName(String friendId) {
        Box<Friend> box = MyApplication.getInstance().getBoxStore().boxFor(Friend.class);
        List<Friend> friendList = box.query().equal(Friend_.friendId, friendId).build().find();
        if (friendList.size() <= 0) {
            return "";
        }
        String name = friendList.get(0).getFriendName();
        Logger.d(TAG, "朋友名字 : " + name);
        return name;
    }

    /**
     * 向数据库添加朋友
     *
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

    /**
     * 获得所有的朋友
     */
    public static List<Friend> getAllFriend() {
        Box<Friend> box = MyApplication.getInstance().getBoxStore().boxFor(Friend.class);
        List<Friend> list = box.query().build().find();
        return list;
    }

    /**
     * 获得所有的朋友
     */
    public static boolean isMyFriend(String name) {
        Box<Friend> box = MyApplication.getInstance().getBoxStore().boxFor(Friend.class);
        List<Friend> list = box.query().equal(Friend_.friendName, name).build().find();

        return list.size() >0 ;
    }


    /**
     * 添加msg
     *
     * @param msg
     */
    public static void addFriendMsg(FriendMsg msg) {
        Box<FriendMsg> box = MyApplication.getInstance().getBoxStore().boxFor(FriendMsg.class);
        box.put(msg);
    }

    /**
     * 获取某位朋友的消息
     *
     * @param friendName
     * @return
     */
    public static List<FriendMsg> getFriendMsg(String friendName) {
        Box<FriendMsg> box = MyApplication.getInstance().getBoxStore().boxFor(FriendMsg.class);
        List<FriendMsg> list = box.query().equal(FriendMsg_.friendId, StringUtil.md5UserId(friendName)).build().find();
        return list;
    }


    public static void clearAllData() {
        Box<Friend> box = MyApplication.getInstance().getBoxStore().boxFor(Friend.class);
        box.removeAll();
        Box<FriendMsg> msgBox = MyApplication.getInstance().getBoxStore().boxFor(FriendMsg.class);
        msgBox.removeAll();
        Logger.d(TAG, "数据库中数据全部删除");
    }
}
