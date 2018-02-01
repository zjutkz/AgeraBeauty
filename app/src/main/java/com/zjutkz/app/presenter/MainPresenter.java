package com.zjutkz.app.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.google.android.agera.BaseObservable;
import com.google.android.agera.Function;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.Result;
import com.google.android.agera.Supplier;
import com.google.android.agera.Updatable;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.kennyc.bottomsheet.BottomSheet;
import com.kennyc.bottomsheet.BottomSheetListener;
import com.zjutkz.app.R;
import com.zjutkz.app.api.BeautyApi;
import com.zjutkz.app.model.Beauty;
import com.zjutkz.app.model.eventbus.LoadEvent;
import com.zjutkz.app.model.eventbus.RouteEvent;
import com.zjutkz.app.router.RouterProtocol;
import com.zjutkz.app.service.BeautyService;
import com.zjutkz.app.utils.AppUtils;
import com.zjutkz.app.utils.PhotoProcessor;
import com.zjutkz.app.view.callback.MainView;
import com.zjutkz.lib.AgeraBus;
import com.zjutkz.powerfulrecyclerview.listener.OnLoadMoreListener;
import com.zjutkz.powerfulrecyclerview.listener.OnRefreshListener;
import com.zjutkz.powerfulrecyclerview.ptr.PowerfulRecyclerView;

import java.util.ArrayList;
import java.util.concurrent.Executors;

/**
 * Created by kangzhe on 16/9/9.
 */
public class MainPresenter extends MvpBasePresenter<MainView> implements Updatable,PowerfulRecyclerView.OnItemClickListener,
        PowerfulRecyclerView.OnItemLongClickListener,OnRefreshListener,OnLoadMoreListener,BottomSheetListener{

    public static final int LOAD_MORE = 100;
    public static final int REFRESH = 101;

    private static final int NET_WORK_ERROR = 200;
    private static final int NET_WORK_SUCCESS = 201;

    private int isSuccess = NET_WORK_SUCCESS;

    private Repository<Beauty> repository;

    private UpdatableObserver eventSource = new UpdatableObserver();

    private Context context;
    
    private int lastAction;

    private int page;

    private Beauty beauties;

    private int lastPosition;

    private Function<Throwable,Beauty> errorHandler = new Function<Throwable, Beauty>() {
        @NonNull
        @Override
        public Beauty apply(@NonNull Throwable input) {
            input.printStackTrace();
            isSuccess = NET_WORK_ERROR;
            return new Beauty();
        }
    };

    public MainPresenter(Context context){
        this.context = context;

        page = 1;
        beauties = new Beauty();
        beauties.results = new ArrayList<>();

        Beauty beauty = new Beauty();
        repository = Repositories.repositoryWithInitialValue(beauty)
                .observe(eventSource)
                .onUpdatesPerLoop()
                .goTo(Executors.newSingleThreadExecutor())
                .thenAttemptGetFrom(eventSource)
                .orEnd(errorHandler)
                .compile();

        repository.addUpdatable(this);
    }

    public void getBeauty(int type){
        if(type == REFRESH){
            page = 1;
        }

        lastAction = type;

        eventSource.setPage(page++);
        eventSource.update();
    }

    @Override
    public void update() {
        if(isSuccess == NET_WORK_ERROR){
            AgeraBus.eventRepositories().post(new LoadEvent(false,-1,null));
            return;
        }
        if(lastAction == REFRESH){
            beauties.results.clear();
            beauties.results.addAll(repository.get().results);
        }else if(lastAction == LOAD_MORE){
            beauties.results.addAll(repository.get().results);
        }

        AgeraBus.eventRepositories().post(new LoadEvent(true,lastAction,beauties.results));
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
    public boolean onItemLongClick(RecyclerView parent, RecyclerView.ViewHolder holder, final int position) {
        lastPosition = position;
        AppUtils.showBottomSheet(context,this);
        return true;
    }

    public int getLastPosition() {
        return lastPosition;
    }

    public void removeUpdatable(){
        repository.removeUpdatable(this);
    }

    @Override
    public void onSheetShown(@NonNull BottomSheet bottomSheet) {

    }

    @Override
    public void onSheetItemSelected(@NonNull BottomSheet bottomSheet, MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.share:
                PhotoProcessor.getInstance().sharePic(beauties.results.get(lastPosition).url);
                break;
            case R.id.save:
                try {
                    PhotoProcessor.getInstance().savePic(beauties.results.get(lastPosition).url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.wall_paper:
                PhotoProcessor.getInstance().setWallPaper(beauties.results.get(lastPosition).url);
                break;
        }
    }

    @Override
    public void onSheetDismissed(@NonNull BottomSheet bottomSheet, @DismissEvent int i) {

    }

    private static class UpdatableObserver extends BaseObservable implements Supplier<Result<Beauty>>{

        private int page;

        private BeautyApi api;

        public UpdatableObserver(){
            this(1);
        }

        public UpdatableObserver(int page){
            this.page = page;

            api = BeautyService.getInstance().getNetEngine().create(BeautyApi.class);
        }

        public void setPage(int page){
            this.page = page;
        }

        public void update(){
            dispatchUpdate();
        }

        @NonNull
        @Override
        public Result<Beauty> get() {
            return api.getBeautyInfo(page).get();
        }
    }
}
