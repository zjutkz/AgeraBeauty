package com.zjutkz.app.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.kennyc.bottomsheet.BottomSheet;
import com.kennyc.bottomsheet.BottomSheetListener;
import com.zjutkz.app.R;
import com.zjutkz.app.adapter.GalleryAdapter;
import com.zjutkz.app.model.Beauty;
import com.zjutkz.app.model.eventbus.PageChangedEvent;
import com.zjutkz.app.utils.PhotoProcessor;
import com.zjutkz.app.view.callback.GalleryView;
import com.zjutkz.lib.AgeraBus;

import java.util.List;

/**
 * Created by kangzhe on 16/9/9.
 */
public class GalleryPresenter extends MvpBasePresenter<GalleryView> implements ViewPager.OnPageChangeListener,BottomSheetListener{

    private Beauty beauties;

    private Beauty.BeautyEntity currentBeauty;

    private Context context;

    public GalleryPresenter(Context context,int startPosition,Beauty beauties){
        this.context = context;
        this.beauties = beauties;
        currentBeauty = beauties.results.get(startPosition);
    }

    public List<Beauty.BeautyEntity> getBeauties(){
        return beauties.results;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        AgeraBus.eventRepositories().post(new PageChangedEvent(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSheetShown(@NonNull BottomSheet bottomSheet) {

    }

    // TODO: 16/9/17 because using infinitecycleviewpager,item index is incorrect(more han 50000,is it a bug for this component??),
    // TODO: 16/9/17 so I just using static variable,any elegant way?!
    @Override
    public void onSheetItemSelected(@NonNull BottomSheet bottomSheet, MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.share:
                PhotoProcessor.getInstance().sharePic(GalleryAdapter.sNeedProceedBeauty);
                break;
            case R.id.save:
                try {
                    PhotoProcessor.getInstance().savePic(GalleryAdapter.sNeedProceedBeauty);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.wall_paper:
                PhotoProcessor.getInstance().setWallPaper(GalleryAdapter.sNeedProceedBeauty);
                break;
        }
    }

    @Override
    public void onSheetDismissed(@NonNull BottomSheet bottomSheet, @DismissEvent int i) {

    }
}
