package com.zjutkz.app.presenter;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.zjutkz.app.model.eventbus.RouteEvent;
import com.zjutkz.app.router.RouterProtocol;
import com.zjutkz.app.view.callback.SplashView;
import com.zjutkz.lib.AgeraBus;

/**
 * Created by kangzhe on 16/9/9.
 */
public class SplashPresenter extends MvpBasePresenter<SplashView> {

    private static final long DELAY_TIME = 3000;

    public void startCountDown(){
        AgeraBus.eventRepositories().postDelay(new RouteEvent(RouterProtocol.MAIN,null),DELAY_TIME);
    }
}
