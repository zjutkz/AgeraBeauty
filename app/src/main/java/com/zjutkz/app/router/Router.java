package com.zjutkz.app.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.zjutkz.app.view.GalleryActivity;
import com.zjutkz.app.view.MainActivity;
import com.zjutkz.app.view.SpecificActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kangzhe on 16/9/9.
 */
public class Router implements IRouter{

    private static volatile Router instance;

    public interface IntentHooker {
        void hookIntent(Intent intent);
    }

    public List<IntentHooker> hookers;

    public static Router getInstance() {
        if(instance == null){
            synchronized (Router.class){
                if(instance == null){
                    instance = new Router();
                }
            }
        }
        return instance;
    }

    public Router(){
        hookers = new ArrayList<>();
    }
    private void routeToSomePage(Context context,Intent intent){
        if(!(context instanceof Activity)){
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        for(IntentHooker hooker : hookers){
            hooker.hookIntent(intent);
        }

        context.startActivity(intent);
    }

    @Override
    public IRouter hookIntent(IntentHooker hooker){
        hookers.add(hooker);

        return this;
    }

    @Override
    public IRouter route(Context context,String protocol) {
        Intent intent = new Intent();
        switch(protocol){
            case RouterProtocol.MAIN:
                intent.setClass(context,MainActivity.class);
                break;
            case RouterProtocol.GALLERY:
                intent.setClass(context,GalleryActivity.class);
                break;
            case RouterProtocol.SPECIFIC:
                intent.setClass(context, SpecificActivity.class);
        }
        routeToSomePage(context,intent);

        return this;
    }
}
