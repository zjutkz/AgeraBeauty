package com.zjutkz.app.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.zjutkz.app.R;
import com.zjutkz.app.adapter.GalleryAdapter;
import com.zjutkz.app.constants.IntentConstants;
import com.zjutkz.app.model.Beauty;
import com.zjutkz.app.model.eventbus.BottomMenuEvent;
import com.zjutkz.app.model.eventbus.PageChangedEvent;
import com.zjutkz.app.model.eventbus.RouteEvent;
import com.zjutkz.app.presenter.GalleryPresenter;
import com.zjutkz.app.router.Router;
import com.zjutkz.app.router.RouterProtocol;
import com.zjutkz.app.utils.AppUtils;
import com.zjutkz.app.view.callback.GalleryView;
import com.zjutkz.app.view.transformer.ScaleTransformer;
import com.zjutkz.lib.AgeraBus;
import com.zjutkz.lib.listener.OnEventReceiveListener;

/**
 * Created by kangzhe on 16/9/9.
 */
public class GalleryActivity extends MvpActivity<GalleryView,GalleryPresenter> implements OnEventReceiveListener{

    private static final String TAG = "GalleryActivity";

    private ViewPager viewPager;
    private GalleryAdapter adapter;

    private TextView indicator;

    private int startPosition = 0;
    private int currentPosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        initData();
        initView();
        initIndicator();

        AgeraBus.eventRepositories().registerInMainThread(this);
    }

    private void initData() {
        startPosition = getIntent().getIntExtra(IntentConstants.GALLERY_START,0);
        currentPosition = startPosition;
    }

    @Override
    protected void onResume() {
        super.onResume();

        initAdapter();
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
        viewPager.setCurrentItem(currentPosition);
    }

    @NonNull
    @Override
    public GalleryPresenter createPresenter() {
        Intent intent = getIntent();
        if(intent != null && intent.getSerializableExtra(IntentConstants.GALLERY_DATA) != null
                && intent.getSerializableExtra(IntentConstants.GALLERY_DATA) instanceof Beauty){
            return new GalleryPresenter(this,startPosition,(Beauty)intent.getSerializableExtra(IntentConstants.GALLERY_DATA));
        }

        Log.e(TAG, "intent data is null!");
        return new GalleryPresenter(this,startPosition,null);
    }

    private void initView() {
        viewPager = (ViewPager)findViewById(R.id.gallery);
        viewPager.setPageTransformer(true,new ScaleTransformer());
        viewPager.addOnPageChangeListener(getPresenter());

        indicator = (TextView)findViewById(R.id.indicator);
    }

    @Override
    public void onEventReceiveInMain() {
        if(AgeraBus.eventRepositories().get() instanceof PageChangedEvent){
            currentPosition = ((PageChangedEvent) AgeraBus.eventRepositories().get()).currentPosition;
            changeIndicator(currentPosition);
        }else if(AgeraBus.eventRepositories().get() instanceof RouteEvent){
            final RouteEvent event = (RouteEvent) AgeraBus.eventRepositories().get();
            if(RouterProtocol.SPECIFIC.equals(event.protocol))
            Router.getInstance().hookIntent(new Router.IntentHooker() {
                @Override
                public void hookIntent(Intent intent) {
                    intent.putExtra(IntentConstants.SPECIFIC_DATA,(String)event.bundle);
                }
            }).route(this,event.protocol);
        }else if(AgeraBus.eventRepositories().get() instanceof BottomMenuEvent){
            AppUtils.showBottomSheet(this,getPresenter());
        }
    }

    private void changeIndicator(int position) {
        indicator.setText((position + 1) + " / " + getPresenter().getBeauties().size());
    }

    @Override
    public void onEventReceiveInBackground() {

    }
}
