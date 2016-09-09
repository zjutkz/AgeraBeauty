package com.zjutkz.app.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.zjutkz.app.view.MainActivity;

/**
 * Created by kangzhe on 16/9/9.
 */
public class Router {

    public static void routeToMainPage(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        if(!(context instanceof Activity)){
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        context.startActivity(intent);
    }
}
