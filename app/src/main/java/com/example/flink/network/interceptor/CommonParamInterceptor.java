package com.example.flink.network.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CommonParamInterceptor implements Interceptor {
    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";
    private Map<String, String> commonParamsMap = new HashMap<>();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();

        if (METHOD_POST.equals(request.method())) {
            if (commonParamsMap.size() > 0) {
                FormBody.Builder bodyBuilder = new FormBody.Builder();
                // 把已有的post参数添加到新的构造器
                if (request.body() instanceof FormBody) {
                    FormBody formBody = (FormBody) request.body();
                    for (int i = 0; i < formBody.size(); i++) {
                        bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                    }
                }
                //添加公共参数
                for (Map.Entry<String, String> params : commonParamsMap.entrySet()) {
                    bodyBuilder.addEncoded(params.getKey(), params.getValue());
                }
                //最终的表单body填充到request中
                requestBuilder.post(bodyBuilder.build());
            }
        }

        return chain.proceed(requestBuilder.build());
    }

    public CommonParamInterceptor addBodyParam(String key, String value) {
        commonParamsMap.put(key, value);
        return this;
    }

}
