package com.lgdev.roomdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.MyViewHolder> {
    private static final String TAG = AdapterUser.class.getSimpleName();

    Context context;
    List<User> list;

    public AdapterUser(Context context, List<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_rv_user_record, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User model = list.get(position);

        holder.rv_text_user_name.setText(String.format("%s %s", model.getFirstName(), model.getLastName()));
        holder.rv_text_user_contact.setText(model.getContact());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView rv_text_user_name, rv_text_user_contact;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rv_text_user_contact = itemView.findViewById(R.id.rv_text_user_contact);
            rv_text_user_name = itemView.findViewById(R.id.rv_text_user_name);
        }
    }
}
