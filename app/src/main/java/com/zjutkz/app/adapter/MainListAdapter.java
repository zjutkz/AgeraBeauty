package com.zjutkz.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zjutkz.app.R;
import com.zjutkz.app.model.Beauty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kangzhe on 16/9/9.
 */
public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.MainListViewHolder>{

    private Context context;
    private List<Beauty.BeautyEntity> beauties = new ArrayList<>();

    public MainListAdapter(Context context){
        this.context = context;
    }

    public void refreshBeauties(List<Beauty.BeautyEntity> beauties){
        this.beauties.clear();
        this.beauties.addAll(beauties);
        notifyDataSetChanged();
    }

    public void appendBeautiesToTail(List<Beauty.BeautyEntity> beauties){
        this.beauties.addAll(beauties);
        notifyDataSetChanged();
    }

    @Override
    public MainListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_list,parent,false));
    }

    @Override
    public int getItemCount() {
        return beauties.size();
    }

    @Override
    public void onBindViewHolder(MainListViewHolder holder, int position) {
        Beauty.BeautyEntity beauty = beauties.get(position);

        Glide.with(context)
                .load(beauty.url)
                .centerCrop()
                .into(holder.mainListIv);
    }

    public static class MainListViewHolder extends RecyclerView.ViewHolder{

        private ImageView mainListIv;

        public MainListViewHolder(View itemView) {
            super(itemView);

            mainListIv = (ImageView)itemView.findViewById(R.id.main_list_iv);
        }
    }
}
