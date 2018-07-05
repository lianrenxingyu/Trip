package com.chenggong.trip.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenggong.trip.R;
import com.chenggong.trip.bean.News;
import com.chenggong.trip.ui.ChatActivity;

import java.util.List;

/**
 * Created by chenggong on 18-5-1.
 *
 * @author chenggong
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private static final String TAG = "NewsAdapter";

    private List<News> newsList;
    private Context context;

    private OnItemClickListener itemClickListener;

    public NewsAdapter(Context context, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder holder, int position) {
        final News news = newsList.get(position);
        holder.tv_name.setText(news.getName());
        holder.tv_brief_msg.setText(news.getBrief_msg());
        holder.tv_time.setText(news.getTime());

        //图片设置存在问题
        holder.iv_head_image.setImageResource(R.mipmap.ic_launcher_round);
        holder.news_item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo : 打开对话界面
                ChatActivity.start(context,news.getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        ConstraintLayout news_item_layout;
        ImageView iv_head_image;
        TextView tv_name;
        TextView tv_brief_msg;
        TextView tv_time;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            news_item_layout = itemView.findViewById(R.id.news_item_layout);
            iv_head_image = itemView.findViewById(R.id.iv_head_image);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_brief_msg = itemView.findViewById(R.id.tv_brief_msg);
            tv_time = itemView.findViewById(R.id.tv_time);
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);

        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
