package com.chenggong.trip.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chenggong.trip.R;
import com.chenggong.trip.db.DbHelper;
import com.chenggong.trip.net.HttpUtil;
import com.chenggong.trip.util.Configure;
import com.chenggong.trip.util.Logger;
import com.chenggong.trip.util.StringUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by chenggong on 18-5-8.
 *
 * @author chenggong
 */

public class AddFriendActivity extends BaseActivity {

    private static final String TAG = "AddFriendActivity";
    private static final String ADD_FRIEND_URL = "http://192.168.43.254:8080/TripServer1_1/addFriend";
    private Toolbar toolbar;
    private TextView tv_addFriend;
    private EditText et_friendName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        et_friendName = findViewById(R.id.et_friendName);

        tv_addFriend = findViewById(R.id.tv_addFriend);
        tv_addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String friendName = et_friendName.getText().toString();
                String friendId = StringUtil.md5UserId(friendName);
                Map<String, String> map = new HashMap();
                map.put("userId", Configure.localUser.getUserId());
                map.put("friendId", friendId);
                HttpUtil.sendFormRequest(ADD_FRIEND_URL, map, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseStr = response.body().string();
                        Logger.d(TAG, responseStr);
                        //todo 添加好友情况
                        final JSONObject object = JSON.parseObject(responseStr);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                boolean hasFriend = object.getBoolean("hasFriend");
                                boolean isMyFriend = object.getBoolean("isMyFriend");
                                boolean isAddSuccess = object.getBoolean("isAddSuccess");
                                if (!hasFriend){
                                    Toast.makeText(AddFriendActivity.this, "没有这个账号", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (isMyFriend){
                                    Logger.d(TAG,"服务器中已经存在好友关系");
                                    if (!DbHelper.isMyFriend(friendName)){
                                        DbHelper.addFriend(friendName);
                                    }
                                    Toast.makeText(AddFriendActivity.this, "已经是好友了,不能添加", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if(isAddSuccess){
                                    DbHelper.addFriend(friendName);
                                    Toast.makeText(AddFriendActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });
            }
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_white);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public static void start(Context context) {
        Intent intent = new Intent(context, AddFriendActivity.class);
        context.startActivity(intent);
    }
}
