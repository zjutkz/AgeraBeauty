package com.zjutkz.app.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zjutkz.app.R;
import com.zjutkz.app.model.Beauty;
import com.zjutkz.app.model.eventbus.RouteEvent;
import com.zjutkz.app.router.RouterProtocol;
import com.zjutkz.lib.AgeraBus;

import java.util.List;

/**
 * Created by kangzhe on 16/9/9.
 */
public class GalleryAdapter extends PagerAdapter{

    private List<Beauty.BeautyEntity> beauties;

    public GalleryAdapter(List<Beauty.BeautyEntity> beauties){
        this.beauties = beauties;
    }

    @Override
    public int getCount() {
        return beauties.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_gallery,container,false);
        ImageView iv = (ImageView)view.findViewById(R.id.gallery_iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AgeraBus.eventRepositories().post(new RouteEvent(RouterProtocol.SPECIFIC,beauties.get(position).url));
            }
        });
        Glide.with(container.getContext())
                .load(beauties.get(position).url)
                .centerCrop()
                .placeholder(R.drawable.splash)
                .into(iv);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }
}
