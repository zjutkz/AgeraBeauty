package com.zjutkz.app.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.zjutkz.app.R;
import com.zjutkz.app.constants.IntentConstants;
import com.zjutkz.app.presenter.SpecificPresenter;
import com.zjutkz.app.view.callback.SpecificView;

import pl.droidsonroids.gif.GifImageView;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by kangzhe on 16/9/10.
 */
public class SpecificActivity extends MvpActivity<SpecificView,SpecificPresenter> implements SpecificView{

    private static final String TAG = "SpecificActivity";

    private String beauty;

    private GifImageView loading;
    private PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific);

        initView();
        showBeauty();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setListener();
    }

    private void setListener() {
        photoView.setOnLongClickListener(getPresenter());
    }

    private void showBeauty() {
        Glide.with(this)
                .load(beauty)
                .centerCrop()
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        loading.setVisibility(View.GONE);
                        photoView.setVisibility(View.VISIBLE);
                        photoView.setImageDrawable(resource.getCurrent());
                    }
                });
    }

    private void initView() {
        loading = (GifImageView)findViewById(R.id.specific_loading);
        photoView = (PhotoView)findViewById(R.id.photo_view);
    }

    @NonNull
    @Override
    public SpecificPresenter createPresenter() {
        if(getIntent() != null){
            beauty = getIntent().getStringExtra(IntentConstants.SPECIFIC_DATA);
        }

        if(!TextUtils.isEmpty(beauty)){
            return new SpecificPresenter(beauty,this);
        }

        Log.e(TAG, "intent data is null!");
        return new SpecificPresenter(null,this);
    }
}
