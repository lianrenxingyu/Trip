package com.chenggong.trip.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chenggong.trip.R;
import com.chenggong.trip.net.HttpUtil;
import com.chenggong.trip.util.Logger;
import com.chenggong.trip.util.StringUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterActivity extends BaseActivity {

    private static final String TAG = "RegisterActivity";
    private static final String REGISTER_URL = "http://192.168.43.254:8080/TripServer1_1/register";

    private Button btn_register;
    private EditText et_userName, et_first_pwd, et_second_pwd, et_email, et_company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btn_register = findViewById(R.id.btn_register);
        et_userName = findViewById(R.id.userName);
        et_first_pwd = findViewById(R.id.first_password);
        et_second_pwd = findViewById(R.id.second_password);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  TODO 检查输入是否符合规范，提交数据，返回登录界面,登录操作,不正确的字符限制和检测
                final String username = et_userName.getText().toString().trim();
                final String first_password = et_first_pwd.getText().toString();
                String second_password = et_second_pwd.getText().toString();

                //密码是否相同
                if (first_password.equals(second_password)) {
                    //服务器不存储密码
                    String passwordMD5 = StringUtil.md5(first_password, 16);
                    Map<String, String> map = new HashMap<>();
                    map.put("username", username);
                    map.put("password", passwordMD5);
                    HttpUtil.sendFormRequest(REGISTER_URL, map, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String responseStr = response.body().string();
                            Logger.d(TAG, responseStr);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                    LoginActivity.start(RegisterActivity.this,username,first_password);
                                    finish();
                                }
                            });
                        }
                    });

                } else {
                    //两次密码不同
                    et_first_pwd.setText("");
                    et_second_pwd.setText("");
                    et_first_pwd.requestFocus();
                    Toast.makeText(RegisterActivity.this, "两次输入密码不同", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }
}
