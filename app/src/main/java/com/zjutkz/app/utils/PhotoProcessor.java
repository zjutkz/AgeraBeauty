package com.zjutkz.app.utils;

import android.os.Looper;
import android.support.annotation.NonNull;

import com.google.android.agera.Function;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.Supplier;
import com.google.android.agera.Updatable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/**
 * Created by kangzhe on 16/9/11.
 */
public class PhotoProcessor implements Updatable{

    private static final int ACTION_SHARE = 100;
    private static final int ACTION_SAVE = 101;
    private static final int ACTION_WALL_PAPER = 102;

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    private Repository<String> repo;

    private int lastAction;

    private static volatile PhotoProcessor instance;

    public static PhotoProcessor getInstance(){
        if(instance == null){
            synchronized (PhotoProcessor.class){
                if(instance == null){
                    instance = new PhotoProcessor();
                }
            }
        }

        return instance;
    }

    private Function<Throwable,String> errorHandler = new Function<Throwable, String>() {
        @NonNull
        @Override
        public String apply(@NonNull Throwable input) {
            return FAIL;
        }
    };

    private Supplier<String> successHandler = new Supplier<String>() {
        @NonNull
        @Override
        public String get() {
            return SUCCESS;
        }
    };

    public void savePic(final String data) throws ExecutionException, InterruptedException {
        lastAction = ACTION_SAVE;

        repo = Repositories.repositoryWithInitialValue(data)
                .observe()
                .onUpdatesPerLoop()
                .goLazy()
                .attemptTransform(FileUtils.savePic())
                .orEnd(errorHandler)
                .thenGetFrom(successHandler)
                .compile();

        addToUpdatableInBackground();
    }

    public void sharePic(final String data) {
        lastAction = ACTION_SHARE;

        repo = Repositories.repositoryWithInitialValue(data)
                .observe()
                .onUpdatesPerLoop()
                .goLazy()
                .attemptTransform(FileUtils.sharePic())
                .orEnd(errorHandler)
                .thenGetFrom(successHandler)
                .compile();

        addToUpdatableInBackground();
    }

    public void setWallPaper(final String data){
        lastAction = ACTION_WALL_PAPER;

        repo = Repositories.repositoryWithInitialValue(data)
                .observe()
                .onUpdatesPerLoop()
                .goLazy()
                .attemptTransform(WallPaperUtils.changeWallPageFunction)
                .orEnd(errorHandler)
                .thenGetFrom(successHandler)
                .compile();

        addToUpdatableInBackground();
    }

    private void addToUpdatableInBackground() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                repo.addUpdatable(PhotoProcessor.this);
                Looper.loop();
            }
        });
    }

    @Override
    public void update() {
        if(SUCCESS.equals(repo.get())){
            if(lastAction == ACTION_SAVE){
                AppUtils.makeToast(AppUtils.getAppContext(),"保存图片成功~");
            }else if(lastAction == ACTION_WALL_PAPER){
                AppUtils.makeToast(AppUtils.getAppContext(),"设置壁纸成功~");
            }
        }else {
            AppUtils.makeToast(AppUtils.getAppContext(),"出错啦>.<~~");
        }
    }
}
