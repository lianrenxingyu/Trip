package com.chenggong.trip.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chenggong.trip.R;
import com.chenggong.trip.bean.User;
import com.chenggong.trip.net.HttpUtil;
import com.chenggong.trip.util.Configure;
import com.chenggong.trip.util.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SplashActivity extends BaseActivity {

    private static final int ANIMATION_DURATION = 1700;//这个数值足够长,完成出事化操作
    private static final String TAG = "SplashActivity";
    private static final String IDENTITY_FILTER_URL = "http://192.168.43.254:8080/TripServer1_1/identify";//身份认证网址

    private ImageView splashImage;

    private String isLogin = "true";

    private String username;
    private String userId;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashImage = findViewById(R.id.splashImage);
        animateImage();

    }

    /**
     * 闪屏界面动画
     */
    private void animateImage() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(splashImage, View.SCALE_X, 1f, 1.13f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(splashImage, View.SCALE_Y, 1f, 1.13f);

        AnimatorSet set = new AnimatorSet();
        set.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                initAndGetUserInfo();
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                if(isLogin.equals("true")){
                    Configure.localUser = new User(username,userId);
                    Configure.token = token;
                    MainActivity.start(SplashActivity.this);
                }else  {
                    LoginActivity.start(SplashActivity.this);

                }
                SplashActivity.this.finish();
            }

        });
        set.setDuration(ANIMATION_DURATION).play(animatorX).with(animatorY);
        set.start();
    }

    private void initAndGetUserInfo() {
        SharedPreferences sp = getSharedPreferences("tripPreferenceFile", MODE_PRIVATE);
        username = sp.getString("username", "");
        userId = sp.getString("userId", "");
        token = sp.getString("token", "");
        if (username.equals("") || userId.equals("") || Configure.token.equals("")) {
            LoginActivity.start(SplashActivity.this);
            Toast.makeText(SplashActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        //登录身份认证
        Map<String, String> map = new HashMap<>();
        HttpUtil.sendFormRequest(IDENTITY_FILTER_URL, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                final JSONObject jsonObject = JSON.parseObject(responseStr);
                isLogin = jsonObject.getString("isLogin");
                Logger.d(TAG, responseStr);
            }
        });
    }
}
