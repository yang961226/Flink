package com.example.flink.tools.data;

import androidx.annotation.Nullable;

import com.example.flink.mInterface.DataInterface;
import com.example.flink.mInterface.VisitDataCallBack;

import java.lang.ref.WeakReference;

/**
 * 数据管理器
 */
public class DataManager implements DataInterface {

    private WeakReference<DataInterface> instance;//目前持有的数据处理机，弱引用防止内存泄漏

    public DataManager(DataManagerEnum num) {
        setDataManager(num);
    }

    public void setDataManager(DataManagerEnum num) {
        switch (num) {
            case SP_DATA_MANAGER:
                instance = new WeakReference<>(SpDataManager.getInstance());
                break;
            case RAM_DATA_MANAGER:
                instance = new WeakReference<>(RamDataManager.getInstance());
                break;
            default:
                instance = new WeakReference<>(SpDataManager.getInstance());
        }
    }

    private DataInterface getInstance() {
        return instance.get();
    }

    public enum DataManagerEnum {

        /**
         * 内存数据管理器，数据存储在内存中，APP关闭后失去数据
         */
        RAM_DATA_MANAGER,

        /**
         * 文件数据管理器，数据存储在SP中，APP关闭后依然保存数据
         */
        SP_DATA_MANAGER,

//        NET_DATA_MANAGER（未来实现）

    }

    @Override
    public void saveInteger(String key, int value, VisitDataCallBack<Integer> visitDataCallBack) {
        getInstance().saveInteger(key, value, visitDataCallBack);
    }

    @Override
    public void saveInteger(String key, int value) {
        getInstance().saveInteger(key, value);
    }

    @Override
    public void saveDouble(String key, double value, VisitDataCallBack<Integer> visitDataCallBack) {
        getInstance().saveDouble(key, value, visitDataCallBack);
    }

    @Override
    public void saveDouble(String key, double value) {
        getInstance().saveDouble(key, value);
    }

    @Override
    public void saveFloat(String key, float value, VisitDataCallBack<Integer> visitDataCallBack) {
        getInstance().saveFloat(key, value, visitDataCallBack);
    }

    @Override
    public void saveFloat(String key, float value) {
        getInstance().saveFloat(key, value);
    }

    @Override
    public void saveString(String key, String value, VisitDataCallBack<Integer> visitDataCallBack) {
        getInstance().saveString(key, value, visitDataCallBack);
    }

    @Override
    public void saveString(String key, String value) {
        getInstance().saveString(key, value);
    }

    @Override
    public void saveBoolean(String key, boolean value, VisitDataCallBack<Integer> visitDataCallBack) {
        getInstance().saveBoolean(key, value, visitDataCallBack);
    }

    @Override
    public void saveBoolean(String key, boolean value) {
        getInstance().saveBoolean(key, value);
    }

    @Override
    public void saveObject(String key, Object object, VisitDataCallBack<Integer> visitDataCallBack) {
        getInstance().saveObject(key, object, visitDataCallBack);
    }

    @Override
    public void saveObject(String key, Object object) {
        getInstance().saveObject(key, object);
    }

    @Override
    public int getInteger(String key, VisitDataCallBack<Integer> visitDataCallBack) {
        return getInstance().getInteger(key, visitDataCallBack);
    }

    @Override
    public int getInteger(String key) {
        return getInstance().getInteger(key);
    }

    @Override
    public double getDouble(String key, VisitDataCallBack<Integer> visitDataCallBack) {
        return getInstance().getDouble(key, visitDataCallBack);
    }

    @Override
    public double getDouble(String key) {
        return getInstance().getDouble(key);
    }

    @Override
    public float getFloat(String key, VisitDataCallBack<Integer> visitDataCallBack) {
        return getInstance().getFloat(key, visitDataCallBack);
    }

    @Override
    public float getFloat(String key) {
        return getInstance().getFloat(key);
    }

    @Override
    public String getString(String key, VisitDataCallBack<Integer> visitDataCallBack) {
        return getInstance().getString(key, visitDataCallBack);
    }

    @Override
    public String getString(String key) {
        return getInstance().getString(key);
    }

    @Override
    public boolean getBoolean(String key, VisitDataCallBack<Integer> visitDataCallBack) {
        return getInstance().getBoolean(key, visitDataCallBack);
    }

    @Override
    public boolean getBoolean(String key) {
        return getInstance().getBoolean(key);
    }

    @Nullable
    @Override
    public Object getObject(String key, VisitDataCallBack<Integer> visitDataCallBack) {
        return getInstance().getObject(key, visitDataCallBack);
    }

    @Nullable
    @Override
    public Object getObject(String key) {
        return getInstance().getObject(key);
    }

}
