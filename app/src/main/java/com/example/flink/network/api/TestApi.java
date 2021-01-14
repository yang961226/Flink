package com.example.flink.network.api;


import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface TestApi {

    @POST
    Observable<String> getResult(@Url String url, @QueryMap Map<String, String> params);

}
