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
import android.widget.Toast;

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

        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_message.getText().toString().equals("")){
                    Toast.makeText(ChatActivity.this, "不能发送空消息", Toast.LENGTH_SHORT).show();
                    return;
                }
                Message msg = new Message(toolbar_title.getText().toString(), et_message.getText().toString(), "right");
                msgList.add(msg);
                adapter.notifyItemInserted(msgList.size()-1);
                recycler_chat.smoothScrollToPosition(msgList.size()-1);
                et_message.setText("");
            }
        });
        init();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recycler_chat.setLayoutManager(layoutManager);
        adapter = new ChatAdapter(this,msgList);
        recycler_chat.setAdapter(adapter);
        recycler_chat.scrollToPosition(msgList.size()-1);

    }

    private  void init (){
        Message msgleft = new Message(toolbar_title.getText().toString(),"拉了福利卡记录卡飞机","left");
        Message msgright = new Message(toolbar_title.getText().toString(),"考减肥的路上咖啡机","right");
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
