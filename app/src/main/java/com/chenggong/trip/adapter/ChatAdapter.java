package com.chenggong.trip.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chenggong.trip.R;
import com.chenggong.trip.bean.Message;

import java.util.List;

/**
 * Created by chenggong on 18-5-11.
 *
 * @author chenggong
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private static final String TAG = "ChatAdapter";
    private List<Message> msgList;
    private Context context;

    public ChatAdapter(Context context, List msgList) {
        this.context = context;
        this.msgList = msgList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.left_msg, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_msg, parent, false);
        }
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message msg = msgList.get(position);
        holder.tv_time.setText(msg.getTime());
        holder.tv_msg.setText(msg.getMsg());
    }

    @Override
    public int getItemCount() {
        return msgList != null ? msgList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        String type = msgList.get(position).getType();
        if (type.equals("left")) {
            return 0;
        }
        if (type.equals("right")) {
            return 1;
        } else {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_time;
        TextView tv_msg;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_msg = itemView.findViewById(R.id.tv_msg);
        }
    }
}
