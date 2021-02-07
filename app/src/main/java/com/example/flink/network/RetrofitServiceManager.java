package com.example.flink.network;

import com.example.flink.network.interceptor.CommonParamInterceptor;
import com.example.flink.network.interceptor.SingleRequestConfigInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitServiceManager {

    private static final String BASE_URL = "http://106.75.236.98:8080/flink/test/";
    private static final int DEFAULT_TIME_OUT = 5;//超时时间
    private static final int DEFAULT_READ_TIME_OUT = 10;

    private Retrofit mRetrofit;
    private static RetrofitServiceManager retrofitServiceManager;

    private RetrofitServiceManager() {
        //创建okhttpClient
        OkHttpClient.Builder okhttpBuilder = new OkHttpClient.Builder();
        okhttpBuilder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        okhttpBuilder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);

        //添加自定义拦截器
        for (Interceptor interceptor : getCustomInterceptor()) {
            okhttpBuilder.addInterceptor(interceptor);
        }

        //创建retrofit
        mRetrofit = new Retrofit.Builder()
                .client(okhttpBuilder.build())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    public static RetrofitServiceManager getInstance() {
        if (retrofitServiceManager == null) {
            synchronized (RetrofitServiceManager.class) {
                if (retrofitServiceManager == null) {
                    retrofitServiceManager = new RetrofitServiceManager();
                }
            }
        }
        return retrofitServiceManager;
    }

    public <T> T create(Class<T> api) {
        return mRetrofit.create(api);
    }

    //生成自定义拦截器
    private List<Interceptor> getCustomInterceptor() {
        List<Interceptor> interceptorList = new ArrayList<>();

        CommonParamInterceptor commonParamInterceptor = new CommonParamInterceptor();
        commonParamInterceptor
                .addBodyParam("公共参数1", "公共参数值1")
                .addBodyParam("公共参数2", "公共参数值2")
                .addBodyParam("公共参数3", "公共参数值3")
                .addBodyParam("公共参数4", "公共参数值4");

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        SingleRequestConfigInterceptor singleRequestConfigInterceptor = new SingleRequestConfigInterceptor();

        interceptorList.add(httpLoggingInterceptor);
        interceptorList.add(commonParamInterceptor);
        interceptorList.add(singleRequestConfigInterceptor);

        return interceptorList;
    }

}
