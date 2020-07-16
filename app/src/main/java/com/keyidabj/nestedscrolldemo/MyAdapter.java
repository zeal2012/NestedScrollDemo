package com.keyidabj.nestedscrolldemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<String> listData;

    public MyAdapter(Context context, List<String> listData_) {
        this.mLayoutInflater = LayoutInflater.from(context);
        listData = listData_;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String txt = listData.get(position);
        ((MyViewHolder)holder).tv_txt.setText(txt);
    }

    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_txt;

        public MyViewHolder(final View itemView) {
            super(itemView);
            tv_txt = itemView.findViewById(R.id.tv_txt);
        }
    }

}
