package com.chenggong.trip.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.chenggong.trip.R;
import com.chenggong.trip.bean.Contact;
import com.chenggong.trip.util.Configure;
import com.chenggong.trip.util.Logger;

/**
 * @author chenggong
 */
public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private BottomNavigationView bottomNavigation;
    private String navigationType = "news";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Fragment newsFragment = new NewsFragment();
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_news:
                        if (!navigationType.equals(Configure.NEWS)) {
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
        changeFragment(newsFragment);
    }

    /**
     * 切换首页的fragment
     */
    private void changeFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d(TAG,"onDestroy");
    }
}
