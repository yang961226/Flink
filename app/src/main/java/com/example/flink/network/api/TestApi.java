package com.example.flink.network.api;


import com.example.flink.network.anno.ConnectionTime;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface TestApi {

    @POST
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @ConnectionTime(connectionTime = 5000)
    Observable<String> getResult(@Url String url, @QueryMap Map<String, String> params);

}
