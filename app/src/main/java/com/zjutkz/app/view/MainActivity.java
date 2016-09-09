package com.zjutkz.app.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.zjutkz.app.R;
import com.zjutkz.app.adapter.MainListAdapter;
import com.zjutkz.app.model.Beauty;
import com.zjutkz.app.model.eventbus.LoadEvent;
import com.zjutkz.app.presenter.MainPresenter;
import com.zjutkz.app.view.callback.MainView;
import com.zjutkz.lib.AgeraBus;
import com.zjutkz.lib.listener.OnEventReceiveListener;
import com.zjutkz.powerfulrecyclerview.ptr.PowerfulRecyclerView;

import java.util.List;

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
            beauties.setLayoutManager(new GridLayoutManager(this,2));
            beauties.setAdapter(adapter);
            beauties.setHeaderView(LayoutInflater.from(this).inflate(R.layout.header_refresh,beauties,false));
            beauties.setFooterView(LayoutInflater.from(this).inflate(R.layout.footer_load_more,beauties,false));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        beauties.setOnRefreshListener(getPresenter());
        beauties.setOnLoadMoreListener(getPresenter());

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
        return new MainPresenter();
    }


    @Override
    public void showBeauties(List<Beauty.BeautyEntity> beauties) {

    }

    @Override
    public void onEventReceiveInMain() {
        if(AgeraBus.eventRepositories().get() instanceof LoadEvent){
            LoadEvent loadEvent = (LoadEvent)AgeraBus.eventRepositories().get();
            if(loadEvent.loadType == MainPresenter.REFRESH){
                if(adapter != null){
                    adapter.refreshBeauties(loadEvent.beauties);
                }
                beauties.stopRefresh();
            }else {
                if(adapter != null){
                    adapter.appendBeautiesToTail(loadEvent.beauties);
                }
                beauties.stopLoadMore();
            }
        }
    }

    @Override
    public void onEventReceiveInBackground() {

    }
}
