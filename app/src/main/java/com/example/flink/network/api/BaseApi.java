package com.example.flink.network.api;

import com.example.flink.network.listener.HttpOnNextListener;

import java.lang.ref.SoftReference;


public class BaseApi<T> {

    /*回调*/
    private SoftReference<HttpOnNextListener<T>> listener;
    /*是否能取消加载框*/
    private boolean cancel;
    /*是否显示加载框*/
    private boolean showProgress;
    /*是否需要缓存处理*/
    private boolean cache;
    /*基础url*/
    private String baseUrl = "https://www.izaodao.com/Api/";
    /*方法-如果需要缓存必须设置这个参数；不需要不用設置*/
    private String method = "";
    /*超时时间-默认6秒*/
    private int connectionTime = 6;
    /*有网情况下的本地缓存时间默认60秒*/
    private int cookieNetWorkTime = 60;
    /*无网络的情况下本地缓存时间默认30天*/
    private int cookieNoNetWorkTime = 24 * 60 * 60 * 30;
    /* 失败后retry次数*/
    private int retryCount = 1;
    /*失败后retry延迟*/
    private long retryDelay = 100;
    /*失败后retry叠加延迟*/
    private long retryIncreaseDelay = 10;
    /*缓存url-可手动设置*/
    private String cacheUrl;

    public BaseApi(HttpOnNextListener listener) {
        this.listener = new SoftReference<HttpOnNextListener<T>>(listener);
    }
}
