package com.zjutkz.app;

import android.app.Application;

import com.zjutkz.app.utils.AppUtils;

/**
 * Created by kangzhe on 16/9/11.
 */
public class BeautyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        AppUtils.setContext(this);
    }
}
