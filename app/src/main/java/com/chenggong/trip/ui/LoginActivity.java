package com.chenggong.trip.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chenggong.trip.R;
import com.chenggong.trip.net.HttpUtil;
import com.chenggong.trip.util.Configure;
import com.chenggong.trip.util.Logger;
import com.chenggong.trip.util.StringUtil;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String LOGIN_URL = "http://192.168.43.254:8080/TripServer1_1/login";

    private static final String TAG = "LoginActivity";
    private TextView tv_register;
    private Button btn_login;

    private EditText et_userName;
    private EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tv_register = findViewById(R.id.tv_register);
        btn_login = findViewById(R.id.btn_login);
        et_userName = findViewById(R.id.userName);
        et_password = findViewById(R.id.password);

        tv_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        if (getIntent().hasExtra("name")) {
            et_userName.setText(getIntent().getStringExtra("name"));
            et_password.setText(getIntent().getStringExtra("password"));
            et_userName.setCursorVisible(false);
        }
    }

    //singltop,singletask启动活动调用此方法
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.hasExtra("name")) {
            et_userName.setText(intent.getStringExtra("name"));
            et_password.setText(intent.getStringExtra("password"));
            et_userName.setCursorVisible(false);
        }
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, String name, String password) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("password", password);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
                //新用户注册
                RegisterActivity.start(LoginActivity.this);
                break;
            case R.id.btn_login:
                // TODO  登录操作,不正确的字符限制和检测
                String username = et_userName.getText().toString().trim();
                String password = et_password.getText().toString();
                String passwordMD5 = StringUtil.md5(password, 16);

                Map<String, String> map = new HashMap<>();
                map.put("username", username);
                map.put("password", passwordMD5);
                HttpUtil.sendFormRequest(LOGIN_URL, map, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        if (e.getCause() instanceof SocketTimeoutException || e.getCause() instanceof ConnectException) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Toast.makeText(LoginActivity.this, "网络连接异常", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseStr = response.body().string();
                        Logger.d(TAG, responseStr);
                        JSONObject object = JSON.parseObject(responseStr);
                        final String loginResult = object.getString("loginResult");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (loginResult.equals(Configure.LoginSuccess)) {
                                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                } else if (loginResult.equals(Configure.LoginFail)) {
                                    Toast.makeText(LoginActivity.this, "账号密码错误", Toast.LENGTH_SHORT).show();
                                }
                                MainActivity.start(LoginActivity.this);
                                finish();
                            }
                        });
                    }
                });
                break;

        }
    }
}
