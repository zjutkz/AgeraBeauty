package com.zjutkz.app.service;

import me.drakeet.retrofit2.adapter.agera.AgeraCallAdapterFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kangzhe on 16/9/9.
 */
public class BeautyService {

    private static volatile BeautyService instance;

    public static BeautyService getInstance() {
        if(instance == null){
            synchronized (BeautyService.class){
                if(instance == null){
                    instance = new BeautyService();
                }
            }
        }
        return instance;
    }

    public Retrofit getNetEngine(){
        return new Retrofit.Builder()
                .baseUrl("http://gank.io/api/search/query/listview/category/福利/")
                .client(new OkHttpClient())
                .addCallAdapterFactory(AgeraCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
