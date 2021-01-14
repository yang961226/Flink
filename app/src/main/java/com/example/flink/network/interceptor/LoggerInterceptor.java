package com.example.flink.network.interceptor;

import com.example.flink.tools.notify.LogUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LoggerInterceptor implements Interceptor {

    private static final String TAG = "-LoggerInterceptor:";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        LogUtil.d(TAG, String.format("发送请求:%s on %s%n%s%n%s",
                request.url(), chain.connection(), request.headers(), request.body()));
        return chain.proceed(request);
    }


}
