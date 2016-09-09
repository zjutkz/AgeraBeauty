package com.zjutkz.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.Updatable;
import com.zjutkz.app.api.BeautyApi;
import com.zjutkz.app.model.Beauty;
import com.zjutkz.app.service.BeautyService;

import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by kangzhe on 16/9/9.
 */
public class MainActivity extends AppCompatActivity implements Updatable{

    private static final String TAG = "MainActivity";

    Repository<Beauty> repository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void get(View view){
        BeautyApi api = BeautyService.getInstance().getNetEngine().create(BeautyApi.class);

        Beauty beauty = new Beauty();
        repository = Repositories.repositoryWithInitialValue(beauty)
                .observe()
                .onUpdatesPerLoop()
                .goTo(Executors.newSingleThreadExecutor())
                .thenAttemptGetFrom(api.getUserInfo(10,1))
                .orSkip()
                .compile();

        repository.addUpdatable(this);
    }

    @Override
    public void update() {
        List<Beauty.BeautyEntity> beautyEntities = repository.get().results;
        for(Beauty.BeautyEntity entity : beautyEntities){
            Log.d(TAG, "update: " + entity.url);
        }
    }
}
