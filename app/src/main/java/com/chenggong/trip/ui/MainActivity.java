package com.chenggong.trip.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.chenggong.trip.R;
import com.chenggong.trip.fragment.ContactsFragment;
import com.chenggong.trip.fragment.NewsFragment;
import com.chenggong.trip.net.SocketUtil;
import com.chenggong.trip.util.Configure;
import com.chenggong.trip.util.Logger;

import org.json.JSONObject;

/**
 * @author chenggong
 */
public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private BottomNavigationView bottomNavigation;
    private String navigationType = "news";//记录当前的fragment类型

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView drawer_navigation;
    private TextView toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDrawerAndToolbar();


        toolbar_title = findViewById(R.id.toolbar_title);
        bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_news:
                        if (!navigationType.equals(Configure.NEWS)) {
                            Fragment newsFragment = new NewsFragment();
                            changeFragment(newsFragment);
                            navigationType = Configure.NEWS;
                        }
                        break;
                    case R.id.navigation_contact:
                        if (!navigationType.equals(Configure.CONTACTS)) {
                            ContactsFragment contactsFragment = new ContactsFragment();
                            changeFragment(contactsFragment);
                            navigationType = Configure.CONTACTS;
                        }
                        break;
                    case R.id.navigation_near:
                        if (!navigationType.equals(Configure.NEAR)) {
                            //todo:附近
                            navigationType = Configure.NEAR;
                        }
                        break;
                }
                return true;
            }
        });
        final Fragment newsFragment = new NewsFragment();//消息界面
        changeFragment(newsFragment);

        /**
         * 网络操作
         */
        SocketUtil.startLongConnect();//打开一个网络长连接
        SocketUtil.receiveMsg(new SocketUtil.ReceiveCallback() {
            @Override
            public void onResponse(String msg) {
                //todo 收到消息后存入数据库,并且操作界面
                Logger.d(TAG,"收到的消息"+msg);
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }


    /**
     * 初始化drawerLayout和toolbar
     */
    private void initDrawerAndToolbar() {

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //右侧抽屉布局打开操作
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
//        toolbar.setNavigationIcon(R.drawable.open_drawer);//设置navigation需要在addDrawerListener之后


        drawer_navigation = findViewById(R.id.drawer_navigation_view);
        drawer_navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //todo : drawer中的navigation视图
                switch (item.getItemId()) {

                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }


    /**
     * 切换首页的fragment
     */
    private void changeFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
        if (fragment instanceof NewsFragment) {
            toolbar_title.setText("消息");
        } else if (fragment instanceof ContactsFragment) {
            toolbar_title.setText("联系人");
        } else {
            //todo:附近的人的title
        }

    }

    @Override
    protected void onDestroy() {
        Logger.d(TAG, "onDestroy");
        SocketUtil.endLongConnect();
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addFriend:
                AddFriendActivity.start(MainActivity.this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
