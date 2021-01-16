package com.example.flink.network.api;

/**
 * 回调信息统一封装类
 */
public class BaseResultEnity<T> {

    //判断标识
    private int ret;

    //提示信息
    private String msg;

    //显示数据（用户关心的数据）
    private T data;

}
