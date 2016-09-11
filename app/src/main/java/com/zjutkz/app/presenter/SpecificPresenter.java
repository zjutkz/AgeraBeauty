package com.zjutkz.app.presenter;

import android.content.Context;
import android.view.View;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.zjutkz.app.utils.AppUtils;
import com.zjutkz.app.view.callback.SpecificView;

/**
 * Created by kangzhe on 16/9/10.
 */
public class SpecificPresenter extends MvpBasePresenter<SpecificView> implements View.OnLongClickListener{

    private String data;

    private Context context;

    public SpecificPresenter(String data, Context context){
        this.data = data;
        this.context = context;
    }

    @Override
    public boolean onLongClick(View v) {
        AppUtils.showSaveOrShareDialog(context,data);
        return true;
    }
}
