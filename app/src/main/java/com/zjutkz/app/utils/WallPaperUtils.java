package com.zjutkz.app.utils;

import android.app.WallpaperManager;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by kangzhe on 16/9/11.
 */
public class WallPaperUtils {

    public static boolean setWallPaper(Context context,String data){
        WallpaperManager mWallManager=WallpaperManager.getInstance(context);
        try {
            File file = Glide.with(context).load(data).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
            mWallManager.setStream(new FileInputStream(file));
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
