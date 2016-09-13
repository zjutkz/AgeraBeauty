package com.zjutkz.app.function;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.agera.Function;
import com.google.android.agera.Result;
import com.zjutkz.app.utils.AppUtils;
import com.zjutkz.app.utils.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by kangzhe on 16/9/13.
 */
public class FunctionProvider {

    public static Function<String,Result<ByteArrayOutputStream>> fileStreamFunction = new Function<String, Result<ByteArrayOutputStream>>() {
        @NonNull
        @Override
        public Result<ByteArrayOutputStream> apply(@NonNull String input) {
            try {
                return Result.success(FileUtils.mkFileToStream(AppUtils.getAppContext(),input));
            } catch (Exception e) {
                e.printStackTrace();
                return Result.failure();
            }
        }
    };

    public static Function<Result<ByteArrayOutputStream>,Result<Boolean>> savePicFunction = new Function<Result<ByteArrayOutputStream>, Result<Boolean>>() {
        @NonNull
        @Override
        public Result<Boolean> apply(@NonNull Result<ByteArrayOutputStream> input) {
            if(input.succeeded()){
                return Result.success(FileUtils.doSavePic(AppUtils.getAppContext(),input.get().toByteArray()));
            }

            return Result.failure();
        }
    };

    public static Function<Result<Boolean>,Result<Boolean>> startShareFunction = new Function<Result<Boolean>, Result<Boolean>>() {
        @NonNull
        @Override
        public Result<Boolean> apply(@NonNull Result<Boolean> input) {
            if(input.succeeded()){
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                File intentFile = new File(FileUtils.getCache());
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(intentFile));
                shareIntent.setType("image/jpg");
                AppUtils.getAppContext().startActivity(Intent.createChooser(shareIntent, "请选择分享方式~").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }

            return input;
        }
    };
}
