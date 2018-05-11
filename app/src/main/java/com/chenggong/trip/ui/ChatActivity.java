package com.chenggong.trip.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenggong.trip.R;
import com.chenggong.trip.adapter.ChatAdapter;
import com.chenggong.trip.bean.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenggong
 */
public class ChatActivity extends BaseActivity {

    private Toolbar toolbar ;
    private TextView toolbar_title;
    private TextView tv_send;
    private EditText et_message;
    private RecyclerView recycler_chat;
    private ChatAdapter adapter ;
    private List<Message> msgList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        toolbar = findViewById(R.id.toolbar);
        toolbar_title = findViewById(R.id.toolbar_title);
        tv_send = findViewById(R.id.tv_send);
        et_message = findViewById(R.id.et_msg);
        recycler_chat = findViewById(R.id.recycler_chat);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatActivity.this.finish();
            }
        });

        toolbar_title.setText(getIntent().getStringExtra("name"));

        init();
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        recycler_chat.setLayoutManager(linearLayout);
        adapter = new ChatAdapter(this,msgList);
        recycler_chat.setAdapter(adapter);

    }

    private  void init (){
        Message msgleft = new Message(toolbar_title.getText().toString(),"msgdksklfjlsjflkdsjlfkj拉了福利卡记录卡飞机","left");
        Message msgright = new Message(toolbar_title.getText().toString(),"考虑对方就撒了会计法肯定撒娇离开房间爱上法拉多少空间里看风景爱上机","right");
        for(int i = 0;i<10;i++ ){
            msgList.add(msgleft);
            msgList.add(msgright);
        }
    }
    public static void start(Context context,String name) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("name",name);
        context.startActivity(intent);
    }
}
