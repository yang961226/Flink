package com.example.flink.network.interceptor;

import android.text.TextUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.flink.common.MyConstants.CONNECT_TIMEOUT_FLAG;
import static com.example.flink.common.MyConstants.READ_TIMEOUT_FLAG;
import static com.example.flink.common.MyConstants.WRITE_TIMEOUT_FLAG;

/**
 * 这个拦截器的作用是根据header传来的配置修改单次的http请求的一些配置，覆盖全局配置
 */
public class SingleRequestConfigInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        int connectTimeout = chain.connectTimeoutMillis();
        int readTimeout = chain.readTimeoutMillis();
        int writeTimeout = chain.writeTimeoutMillis();

        String connectTimeoutNew = request.header(CONNECT_TIMEOUT_FLAG);
        String readTimeoutNew = request.header(READ_TIMEOUT_FLAG);
        String writeTimeoutNew = request.header(WRITE_TIMEOUT_FLAG);

        if (!TextUtils.isEmpty(connectTimeoutNew)) {
            connectTimeout = Integer.parseInt(connectTimeoutNew);
            request = request.newBuilder().removeHeader(connectTimeoutNew).build();
        }
        if (!TextUtils.isEmpty(readTimeoutNew)) {
            readTimeout = Integer.parseInt(readTimeoutNew);
            request = request.newBuilder().removeHeader(readTimeoutNew).build();
        }
        if (!TextUtils.isEmpty(writeTimeoutNew)) {
            writeTimeout = Integer.parseInt(writeTimeoutNew);
            request = request.newBuilder().removeHeader(writeTimeoutNew).build();
        }


        return chain
                .withConnectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .withReadTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .withWriteTimeout(writeTimeout, TimeUnit.MILLISECONDS)
                .proceed(request);
    }

}
