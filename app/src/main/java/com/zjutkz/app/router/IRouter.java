package com.zjutkz.app.router;

import android.content.Context;

/**
 * Created by kangzhe on 16/9/9.
 */
public interface IRouter {

    IRouter route(Context context,String protocol);

    IRouter hookIntent(Router.IntentHooker hooker);
}
