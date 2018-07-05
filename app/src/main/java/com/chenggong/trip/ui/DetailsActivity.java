package com.chenggong.trip.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chenggong.trip.R;

/**
 * 联系人详细信息界面
 *
 * @author chenggong
 */

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, DetailsActivity.class);
        context.startActivity(intent);
    }
}
