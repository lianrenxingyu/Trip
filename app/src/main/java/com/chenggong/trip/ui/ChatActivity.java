package com.chenggong.trip.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.chenggong.trip.R;

/**
 * @author chenggong
 */
public class ChatActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, ChatActivity.class);
        context.startActivity(intent);
    }
}
