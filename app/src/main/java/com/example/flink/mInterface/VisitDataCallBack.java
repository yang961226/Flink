package com.example.flink.mInterface;

/**
 * 访问数据的回调，网络请求等异步请求中可以用
 *
 * @param <T>
 */
public interface VisitDataCallBack<T> {

    /**
     * 成功的回调
     *
     * @param t 存储成功/获取成功的结果
     */
    void onSuccess(T t);

    /**
     * 失败的回调
     *
     * @param failMessage 失败信息
     */
    void onFail(String failMessage);

}
