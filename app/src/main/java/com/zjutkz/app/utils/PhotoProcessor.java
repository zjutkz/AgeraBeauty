package com.zjutkz.app.utils;

import android.os.Looper;
import android.support.annotation.NonNull;
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

    private Repository<Boolean> repo;

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

    public void savePic(final String data) throws ExecutionException, InterruptedException {
        lastAction = ACTION_SAVE;

        Boolean isSuccess = true;
        repo = Repositories.repositoryWithInitialValue(isSuccess)
                .observe()
                .onUpdatesPerLoop()
                .goTo(Executors.newSingleThreadExecutor())
                .goLazy()
                .thenGetFrom(new Supplier<Boolean>() {

                    @NonNull
                    @Override
                    public Boolean get() {
                        return FileUtils.savePic(AppUtils.getAppContext(),data);
                    }
                })
                .compile();

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                repo.addUpdatable(PhotoProcessor.this);
                Looper.loop();
            }
        });
    }

    public void sharePic(final String data) {
        lastAction = ACTION_SHARE;

        Boolean isSuccess = true;
        repo = Repositories.repositoryWithInitialValue(isSuccess)
                .observe()
                .onUpdatesPerLoop()
                .goLazy()
                .thenGetFrom(new Supplier<Boolean>() {

                    @NonNull
                    @Override
                    public Boolean get() {
                        return FileUtils.sharePic(AppUtils.getAppContext(),data);
                    }
                })
                .compile();

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
        if(repo.get()){
            if(lastAction == ACTION_SAVE){
                AppUtils.makeToast(AppUtils.getAppContext(),"保存图片成功!");
            }
        }else {
            AppUtils.makeToast(AppUtils.getAppContext(),"出错啦>.<~~");
        }
    }
}
