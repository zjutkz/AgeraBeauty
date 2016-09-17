package com.zjutkz.app.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.zjutkz.app.R;
import com.zjutkz.app.adapter.MainListAdapter;
import com.zjutkz.app.constants.IntentConstants;
import com.zjutkz.app.model.Beauty;
import com.zjutkz.app.model.eventbus.LoadEvent;
import com.zjutkz.app.model.eventbus.RouteEvent;
import com.zjutkz.app.presenter.MainPresenter;
import com.zjutkz.app.router.Router;
import com.zjutkz.app.router.RouterProtocol;
import com.zjutkz.app.view.callback.MainView;
import com.zjutkz.lib.AgeraBus;
import com.zjutkz.lib.listener.OnEventReceiveListener;
import com.zjutkz.powerfulrecyclerview.ptr.PowerfulRecyclerView;

/**
 * Created by kangzhe on 16/9/9.
 */
public class MainActivity extends MvpActivity<MainView,MainPresenter> implements MainView,OnEventReceiveListener{

    private static final String TAG = "MainActivity";

    private PowerfulRecyclerView beauties;
    private MainListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBus();
        initAdapter();
        initView();
        configToolbar();

        getPresenter().getBeauty(MainPresenter.REFRESH);
    }

    private void configToolbar() {
        TextView title = (TextView)findViewById(R.id.title);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/introduction.TTF");
        title.setTypeface(typeface);
    }

    private void initBus() {
        AgeraBus.eventRepositories().registerInMainThread(this);
    }

    private void initAdapter() {
        adapter = new MainListAdapter(this);
    }

    private void initView() {
        beauties = (PowerfulRecyclerView)findViewById(R.id.beauty_list);
        if (beauties != null) {
            beauties.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
            beauties.setAdapter(adapter);
            beauties.setOnItemClickListener(getPresenter());
            beauties.setOnItemLongClickListener(getPresenter());
            beauties.setFooterView(LayoutInflater.from(this).inflate(R.layout.footer,beauties,false));
            beauties.setHeaderView(LayoutInflater.from(this).inflate(R.layout.header,beauties,false));
            beauties.setOnRefreshListener(getPresenter());
            beauties.setOnLoadMoreListener(getPresenter());
            beauties.setNoDataViewLayout(R.layout.layout_net_work_error);
        }
    }

    public void refresh(View view){
        beauties.hideSpecialInfoView();
        getPresenter().getBeauty(MainPresenter.REFRESH);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AgeraBus.eventRepositories().unRegister(this);
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public void onEventReceiveInMain() {
        Object event = AgeraBus.eventRepositories().get();
        if(event instanceof LoadEvent){
            if(!((LoadEvent) event).isSuccess){
                beauties.showNoDataView();
                return;
            }
            if(((LoadEvent) event).reqType == MainPresenter.REFRESH){
                beauties.stopRefresh();
            }else {
                beauties.stopLoadMore();
            }
            setBeautiesToAdapter((LoadEvent)event);
        }else if(event instanceof RouteEvent){
            RouteEvent routeEvent = (RouteEvent)event;
            if(RouterProtocol.GALLERY.equals(routeEvent.protocol)){
                jumpToGallery(routeEvent);
            }
        }
    }

    private void jumpToGallery(final RouteEvent event) {
        Router.getInstance().hookIntent(new Router.IntentHooker() {
            @Override
            public void hookIntent(Intent intent) {
                intent.putExtra(IntentConstants.GALLERY_DATA,(Beauty)event.bundle);
                intent.putExtra(IntentConstants.GALLERY_START,getPresenter().getLastPosition());
            }
        }).route(this,event.protocol);
    }

    private void setBeautiesToAdapter(LoadEvent event) {
        adapter.refreshBeauties(event.beauties);
    }

    @Override
    public void onEventReceiveInBackground() {

    }


}
