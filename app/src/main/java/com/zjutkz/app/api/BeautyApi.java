package com.zjutkz.app.api;

import com.google.android.agera.Result;
import com.google.android.agera.Supplier;
import com.zjutkz.app.model.Beauty;

import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by kangzhe on 16/9/9.
 */
public interface BeautyApi {

    @GET("count/{count}/page/{page}")
    Supplier<Result<Beauty>> getUserInfo(@Path("count") int count,@Path("page") int page);
}
