package com.zjutkz.app.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by kangzhe on 16/9/11.
 */
public class AppUtils {

    private static Handler handler = new Handler(Looper.getMainLooper());

    private static Context sContext;

    public static void setContext(Context context){
        sContext = context;
    }

    public static Context getAppContext(){
        return sContext;
    }

    public static void makeToast(final Context context, final String text) {
        runInUIThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void runInUIThread(Runnable runnable) {
        runInUIThread(runnable, 0);
    }

    public static void runInUIThread(Runnable runnable, long delayMillis) {
        handler.postDelayed(runnable, delayMillis);
    }

    public static void showSaveOrShareDialog(Context context,final String data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("保存还是分享呀~");
        builder.setMessage("轻轻点击即可保存或者分享妹纸~");

        builder.setPositiveButton("分享", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PhotoProcessor.getInstance().sharePic(data);
            }
        });

        builder.setNegativeButton("保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    PhotoProcessor.getInstance().savePic(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        builder.show();
    }
}
