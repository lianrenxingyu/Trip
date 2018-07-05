package com.chenggong.trip.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenggong.trip.R;
import com.chenggong.trip.bean.Contact;
import com.chenggong.trip.ui.DetailsActivity;

import java.util.List;

/**
 * Created by chenggong on 18-5-1.
 *
 * @author chenggong
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private Context context;
    private List<Contact> contactList;

    public ContactsAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item,parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Contact contact = contactList.get(position);
        holder.iv_head_image.setImageResource(R.mipmap.ic_launcher_round);
        holder.tv_name.setText(contact.getName());
        holder.contact_item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailsActivity.start(context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        ConstraintLayout contact_item_layout;
        ImageView iv_head_image;
        TextView tv_name;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            iv_head_image = itemView.findViewById(R.id.iv_head_image);
            contact_item_layout = itemView.findViewById(R.id.contact_item_layout);
            tv_name = itemView.findViewById(R.id.tv_name);
        }
    }
}
