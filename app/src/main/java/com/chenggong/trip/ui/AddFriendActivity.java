package com.chenggong.trip.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chenggong.trip.R;
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
                String friendName = et_friendName.getText().toString();
                String friendId = StringUtil.md5UserId(friendName);
                Map<String,String> map = new HashMap();
                map.put("userId", Configure.localUser.getUserId());
                map.put("friendId",friendId);
                HttpUtil.sendFormRequest(ADD_FRIEND_URL, map, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseStr = response.body().string();
                        Logger.d(TAG,responseStr);
                    }
                });
            }
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_black);
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
