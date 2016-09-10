package com.zjutkz.app.presenter;

import android.support.v4.view.ViewPager;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.zjutkz.app.model.Beauty;
import com.zjutkz.app.model.eventbus.PageChangedEvent;
import com.zjutkz.app.view.callback.GalleryView;
import com.zjutkz.lib.AgeraBus;

import java.util.List;

/**
 * Created by kangzhe on 16/9/9.
 */
public class GalleryPresenter extends MvpBasePresenter<GalleryView> implements ViewPager.OnPageChangeListener{

    private Beauty beauties;

    public GalleryPresenter(Beauty beauties){
        this.beauties = beauties;
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
}
