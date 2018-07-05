package com.chenggong.trip.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chenggong.trip.R;
import com.chenggong.trip.adapter.ChatAdapter;
import com.chenggong.trip.bean.Message;
import com.chenggong.trip.db.DbHelper;
import com.chenggong.trip.db.bean.FriendMsg;
import com.chenggong.trip.net.SocketUtil;
import com.chenggong.trip.util.Logger;
import com.chenggong.trip.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenggong
 */
public class ChatActivity extends BaseActivity {

    private static final String TAG = "ChatActivity";

    private Toolbar toolbar;
    private TextView toolbar_title;
    private TextView tv_send;
    private EditText et_message;
    private RecyclerView recycler_chat;
    private ChatAdapter adapter;
    private List<Message> msgList = new ArrayList<>();

    private String friendName;

    private boolean flag = true;//线程检测是否有新消息
    private int msgNum;//消息的数目

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
        friendName = getIntent().getStringExtra("name");

        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_message.getText().toString().equals("")) {
                    Toast.makeText(ChatActivity.this, "不能发送空消息", Toast.LENGTH_SHORT).show();
                    return;
                }
                Message msg = new Message(friendName, et_message.getText().toString(), "right");
                msgList.add(msg);
                adapter.notifyItemInserted(msgList.size() - 1);
                recycler_chat.smoothScrollToPosition(msgList.size() - 1);
                et_message.setText("");
                SocketUtil.sendMsg(StringUtil.md5UserId(friendName), msg.getMsg(), new SocketUtil.SendMsgCallback() {
                    @Override
                    public void onFailure(Exception e) {
                        //todo 发送数据出现错误操作
                    }
                });
                //todo 把自己发送的数据存入本地数据库,检查是否有网络,发送成功
            }
        });
        init();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recycler_chat.setLayoutManager(layoutManager);
        adapter = new ChatAdapter(this, msgList);
        recycler_chat.setAdapter(adapter);
        recycler_chat.scrollToPosition(msgList.size() - 1);

        /**
         * 通过后台一直运行的线程,实时检查数据库数据,如果有新的数据到来,直接更新界面
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                flag = true;
                while (flag) {
                    addNewMsg();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void init() {
        List<FriendMsg> friendMsgList = DbHelper.getFriendMsg(toolbar_title.getText().toString());
        msgNum = friendMsgList.size();
        for (FriendMsg friendMsg : friendMsgList) {
            Message msgLeft = new Message(toolbar_title.getText().toString(), friendMsg.getMsg(), "left", friendMsg.getTime(), friendMsg.getDate());
            msgList.add(msgLeft);
        }
        Logger.d(TAG, "朋友发来的消息初始化完成");
        //todo 初始化自己发送的历史消息
    }

    /**
     * 检查是否有新的数据,并更新{@link #msgList} ,也更新消息界面
     */
    private void addNewMsg() {
        List<FriendMsg> friendMsgList = DbHelper.getFriendMsg(toolbar_title.getText().toString());
        //如果有更新的消息,添加到列表中
        if (friendMsgList.size() > msgNum) {
            for (int i = msgNum; i < friendMsgList.size(); i++) {
                Message msgLeft = new Message(toolbar_title.getText().toString(), friendMsgList.get(i).getMsg(),
                        "left", friendMsgList.get(i).getTime(), friendMsgList.get(i).getDate());
                msgList.add(msgLeft);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                    recycler_chat.smoothScrollToPosition(msgList.size() - 1);
                }
            });
            msgNum = friendMsgList.size();
        }
    }

    public static void start(Context context, String name) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("name", name);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        flag = false;
        super.onDestroy();
    }
}
