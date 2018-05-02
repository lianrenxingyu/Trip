package com.chenggong.trip.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chenggong.trip.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by chenggong on 18-4-30.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView toolbar_title;//中央文字标题

    //所有Activity的管理链表
    List<Activity> activities = new ArrayList<>();

    /**
     * @deprecated 在baseActivity中初始化控件是一个错误的做法, 尤其是需要在onCreate中对控件进行操作时
     * 因为要findViewById操作,所以需要子类{@link #setContentView(View)}方法执行之后才能初始化toolbar
     * 所以不能在baseActivity的{@link #onCreate(Bundle)} 方法中执行下面这个方法
     */
    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    protected void setTitle(String title) {
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(title);
    }

    /**
     * 结束所有的Activity
     */
    protected void finishAllActivity() {
        for (Activity aty : activities) {
            aty.finish();
        }
        activities.clear();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activities.add(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activities.remove(this);
    }
}
