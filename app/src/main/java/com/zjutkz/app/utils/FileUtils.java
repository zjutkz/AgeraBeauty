package com.zjutkz.app.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.google.android.agera.Function;
import com.google.android.agera.Functions;
import com.google.android.agera.Result;
import com.zjutkz.app.function.FunctionProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by kangzhe on 16/9/10.
 */
public class FileUtils {

    private static String cacheFileUri;

    public static Function<String,Result<Boolean>> savePic(){

        return Functions.functionFrom(String.class)
                .apply(FunctionProvider.fileStreamFunction)
                .thenApply(FunctionProvider.savePicFunction);
    }

    public static boolean doSavePic(Context context,byte[] target){
        String picName = "AgeraBeauty_" + System.currentTimeMillis() + ".jpg";
        String sdCardPath = "/sdcard/AgeraBeauty/";

        makeRootDirectory(sdCardPath);
        File file = new File(sdCardPath, picName);

        cacheFileUri = file.getPath();

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream out = new FileOutputStream(file);
            out.write(target);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), picName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
        return true;
    }

    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Function<String,Result<Boolean>> sharePic(){
        return Functions.functionFrom(String.class)
                .apply(FunctionProvider.fileStreamFunction)
                .apply(FunctionProvider.savePicFunction)
                .thenApply(FunctionProvider.startShareFunction);
    }

    public static ByteArrayOutputStream mkFileToStream(Context context, String data) throws IOException, ExecutionException, InterruptedException {
        File file = null;
        file = Glide.with(context).load(data).downloadOnly(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL).get();
        FileInputStream stream = new FileInputStream(file);
        ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
        byte[] b = new byte[1000];
        int n;
        while ((n = stream.read(b)) != -1){
            out.write(b, 0, n);
        }
        stream.close();
        out.close();

        return out;
    }

    public static String getCache(){
        return cacheFileUri;
    }
}
