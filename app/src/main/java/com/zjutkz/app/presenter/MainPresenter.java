package com.zjutkz.app.presenter;

import android.support.v7.widget.RecyclerView;

import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.Updatable;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.zjutkz.app.api.BeautyApi;
import com.zjutkz.app.model.Beauty;
import com.zjutkz.app.model.eventbus.LoadEvent;
import com.zjutkz.app.model.eventbus.RouteEvent;
import com.zjutkz.app.router.RouterProtocol;
import com.zjutkz.app.service.BeautyService;
import com.zjutkz.app.view.callback.MainView;
import com.zjutkz.lib.AgeraBus;
import com.zjutkz.powerfulrecyclerview.listener.OnLoadMoreListener;
import com.zjutkz.powerfulrecyclerview.listener.OnRefreshListener;
import com.zjutkz.powerfulrecyclerview.ptr.PowerfulRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by kangzhe on 16/9/9.
 */
public class MainPresenter extends MvpBasePresenter<MainView> implements Updatable,PowerfulRecyclerView.OnItemClickListener,
        PowerfulRecyclerView.OnItemLongClickListener,OnRefreshListener,OnLoadMoreListener{


    public static final int LOAD_MORE = 100;
    public static final int REFRESH = 101;

    private static final int PAGE_SIZE = 10;

    private Repository<Beauty> repository;

    private int lastReqType;

    private int page;

    private Beauty beauties;

    private int lastPosition;

    public MainPresenter(){
        page = 1;
        beauties = new Beauty();
        List<Beauty.BeautyEntity> list = new ArrayList<>();
        beauties.results = list;
    }

    public void getBeauty(int type){
        BeautyApi api = BeautyService.getInstance().getNetEngine().create(BeautyApi.class);

        if(type == REFRESH){
            page = 1;
        }

        lastReqType = type;

        Beauty beauty = new Beauty();
        repository = Repositories.repositoryWithInitialValue(beauty)
                .observe()
                .onUpdatesPerLoop()
                .goTo(Executors.newSingleThreadExecutor())
                .thenAttemptGetFrom(api.getUserInfo(PAGE_SIZE,page++))
                .orSkip()
                .compile();

        repository.addUpdatable(this);
    }

    @Override
    public void update() {
        if(lastReqType == REFRESH){
            beauties.results.clear();
            beauties.results.addAll(repository.get().results);
        }else {
            beauties.results.addAll(repository.get().results);
        }
        AgeraBus.eventRepositories().post(new LoadEvent(beauties.results));
    }

    @Override
    public void onLoadMore() {
        getBeauty(LOAD_MORE);
    }

    @Override
    public void onRefresh() {
        getBeauty(REFRESH);
    }

    @Override
    public void onItemClick(RecyclerView parent, RecyclerView.ViewHolder holder, int position) {
        lastPosition = position;
        AgeraBus.eventRepositories().post(new RouteEvent(RouterProtocol.GALLERY,beauties));
    }

    @Override
    public boolean onItemLongClick(RecyclerView parent, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    public int getLastPosition() {
        return lastPosition;
    }
}
