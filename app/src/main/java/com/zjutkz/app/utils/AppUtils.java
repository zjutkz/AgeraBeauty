package com.zjutkz.app.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.kennyc.bottomsheet.BottomSheet;
import com.kennyc.bottomsheet.BottomSheetListener;
import com.zjutkz.app.R;

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

    public static void showBottomSheet(Context context,BottomSheetListener listener) {
        if (listener != null) {
            new BottomSheet.Builder(context)
                    .setSheet(R.menu.bottom_sheet_menu)
                    .setTitle("Hope you will enjoy it~ (ง •̀_•́)ง")
                    .setListener(listener)
                    .show();
        }
    }
}
