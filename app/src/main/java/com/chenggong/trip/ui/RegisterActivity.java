package com.chenggong.trip.ui;

import android.content.Context;
import android.content.Intent;
import android.media.ToneGenerator;
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
import java.net.ConnectException;
import java.net.SocketTimeoutException;
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
                //  TODO 检查输入是否符合规范,不正确的字符限制和检测,查询数据库中的userId是否已经存在,不能重复注册
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
                            Logger.d(TAG,"注册网络请求失败"+e.getCause().toString());
                            e.printStackTrace();
                            if(e.getCause() instanceof ConnectException ){
                                Logger.d(TAG,"网络连接超时,未连接网络");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(RegisterActivity.this, "网络连接异常", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String responseStr = response.body().string();
                            Logger.d(TAG," token  :"+ responseStr);
                            //todo 并没有考虑注册失败的情况
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
