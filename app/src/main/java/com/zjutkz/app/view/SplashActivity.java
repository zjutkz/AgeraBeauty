package com.zjutkz.app.view;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.zjutkz.app.R;
import com.zjutkz.app.model.eventbus.RouteEvent;
import com.zjutkz.app.presenter.SplashPresenter;
import com.zjutkz.app.router.Router;
import com.zjutkz.app.router.RouterProtocol;
import com.zjutkz.app.view.callback.SplashView;
import com.zjutkz.lib.AgeraBus;
import com.zjutkz.lib.listener.OnEventReceiveListener;

/**
 * Created by kangzhe on 16/9/9.
 */
public class SplashActivity extends MvpActivity<SplashView,SplashPresenter> implements SplashView,OnEventReceiveListener{

    private TextView introduction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initBus();
        initView();
        setFont();
    }

    private void initBus() {
        AgeraBus.eventRepositories().registerInMainThread(this);
    }

    private void setFont() {
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/introduction.TTF");
        introduction.setTypeface(typeface);
    }

    private void initView() {
        introduction = (TextView)findViewById(R.id.introduction);
    }

    @Override
    protected void onResume() {
        super.onResume();

        countDown();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AgeraBus.eventRepositories().unRegister(this);
    }

    private void countDown() {
        getPresenter().startCountDown();
    }

    @NonNull
    @Override
    public SplashPresenter createPresenter() {
        return new SplashPresenter();
    }

    @Override
    public void onEventReceiveInMain() {
        if(AgeraBus.eventRepositories().get() instanceof RouteEvent){
            RouteEvent routeEvent = (RouteEvent) AgeraBus.eventRepositories().get();
            if(RouterProtocol.MAIN.equals(routeEvent.protocol)){
                Router.getInstance().route(this,routeEvent.protocol);
                finish();
            }
        }
    }

    @Override
    public void onEventReceiveInBackground() {

    }
}
