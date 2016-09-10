package com.zjutkz.app.view;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.zjutkz.app.R;
import com.zjutkz.app.adapter.GalleryAdapter;
import com.zjutkz.app.constants.IntentConstants;
import com.zjutkz.app.model.Beauty;
import com.zjutkz.app.model.eventbus.PageChangedEvent;
import com.zjutkz.app.presenter.GalleryPresenter;
import com.zjutkz.app.view.callback.GalleryView;
import com.zjutkz.app.view.transformer.ScaleTransformer;
import com.zjutkz.lib.AgeraBus;
import com.zjutkz.lib.listener.OnEventReceiveListener;

/**
 * Created by kangzhe on 16/9/9.
 */
public class GalleryActivity extends MvpActivity<GalleryView,GalleryPresenter> implements OnEventReceiveListener{

    private ViewPager viewPager;
    private GalleryAdapter adapter;

    private TextView indicator;

    private int startPosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        initData();
        initView();

        AgeraBus.eventRepositories().registerInMainThread(this);
    }

    private void initData() {
        startPosition = getIntent().getIntExtra(IntentConstants.GALLERY_START,0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        initAdapter();
        initIndicator();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        AgeraBus.eventRepositories().unRegister(this);
    }

    private void initIndicator() {
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/introduction.TTF");
        indicator.setTypeface(typeface);
        changeIndicator(startPosition);
    }

    private void initAdapter() {
        adapter = new GalleryAdapter(getPresenter().getBeauties());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(startPosition);

        indicator = (TextView)findViewById(R.id.indicator);
    }

    @NonNull
    @Override
    public GalleryPresenter createPresenter() {
        return new GalleryPresenter((Beauty)getIntent().getSerializableExtra(IntentConstants.GALLERY_DATA));
    }

    private void initView() {
        viewPager = (ViewPager)findViewById(R.id.gallery);
        viewPager.setPageTransformer(true,new ScaleTransformer());
        viewPager.addOnPageChangeListener(getPresenter());
    }

    @Override
    public void onEventReceiveInMain() {
        if(AgeraBus.eventRepositories().get() instanceof PageChangedEvent){
            int position = ((PageChangedEvent) AgeraBus.eventRepositories().get()).currentPosition;
            changeIndicator(position);
        }
    }

    private void changeIndicator(int position) {
        indicator.setText(position + " / " + getPresenter().getBeauties().size());
    }

    @Override
    public void onEventReceiveInBackground() {

    }
}
