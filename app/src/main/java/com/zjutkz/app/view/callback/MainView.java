package com.zjutkz.app.view.callback;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.zjutkz.app.model.Beauty;

import java.util.List;

/**
 * Created by kangzhe on 16/9/9.
 */
public interface MainView extends MvpView{

    void showBeauties(List<Beauty.BeautyEntity> beauties);
}
