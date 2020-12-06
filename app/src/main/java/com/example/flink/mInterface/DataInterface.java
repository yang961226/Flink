package com.example.flink.mInterface;

import androidx.annotation.Nullable;

public interface DataInterface {

    /**
     * 保存整型数
     *
     * @param key               键名
     * @param value             值
     * @param visitDataCallBack 回调
     */
    void saveInteger(String key, int value, VisitDataCallBack<Integer> visitDataCallBack);

    void saveInteger(String key, int value);

    /**
     * 保存浮点数
     *
     * @param key               键名
     * @param value             值
     * @param visitDataCallBack 回调
     */
    void saveDouble(String key, double value, VisitDataCallBack<Integer> visitDataCallBack);

    void saveDouble(String key, double value);

    /**
     * 保存浮点数
     *
     * @param key               键名
     * @param value             值
     * @param visitDataCallBack 回调
     */
    void saveFloat(String key, float value, VisitDataCallBack<Integer> visitDataCallBack);

    void saveFloat(String key, float value);

    /**
     * 保存字符串
     *
     * @param key               键名
     * @param value             值
     * @param visitDataCallBack 回调
     */
    void saveString(String key, String value, VisitDataCallBack<Integer> visitDataCallBack);

    void saveString(String key, String value);

    /**
     * 保存布尔值
     *
     * @param key               键名
     * @param value             值
     * @param visitDataCallBack 回调
     */
    void saveBoolean(String key, boolean value, VisitDataCallBack<Integer> visitDataCallBack);

    void saveBoolean(String key, boolean value);

    /**
     * 保存类
     *
     * @param key               键名
     * @param object            值
     * @param visitDataCallBack 回调
     */
    void saveObject(String key, Object object, VisitDataCallBack<Integer> visitDataCallBack);

    void saveObject(String key, Object object);

    ///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 获取整型数
     *
     * @param key               键名
     * @param visitDataCallBack 回调
     * @return 值
     */
    int getInteger(String key, VisitDataCallBack<Integer> visitDataCallBack);

    int getInteger(String key);

    /**
     * 获取浮点数
     *
     * @param key               键名
     * @param visitDataCallBack 回调
     * @return 值
     */
    double getDouble(String key, VisitDataCallBack<Integer> visitDataCallBack);

    double getDouble(String key);

    /**
     * 获取浮点数
     *
     * @param key               键名
     * @param visitDataCallBack 回调
     * @return 值
     */
    float getFloat(String key, VisitDataCallBack<Integer> visitDataCallBack);

    float getFloat(String key);

    /**
     * 获取字符串
     *
     * @param key               键名
     * @param visitDataCallBack 回调
     * @return 值
     */
    String getString(String key, VisitDataCallBack<Integer> visitDataCallBack);

    String getString(String key);

    /**
     * 获取布尔值
     *
     * @param key               键名
     * @param visitDataCallBack 回调
     * @return 值
     */
    boolean getBoolean(String key, VisitDataCallBack<Integer> visitDataCallBack);

    boolean getBoolean(String key);

    /**
     * 获取对象
     *
     * @param key               键名
     * @param visitDataCallBack 回调
     * @return 对象
     */
    @Nullable
    Object getObject(String key, VisitDataCallBack<Integer> visitDataCallBack);

    @Nullable
    Object getObject(String key);
}

