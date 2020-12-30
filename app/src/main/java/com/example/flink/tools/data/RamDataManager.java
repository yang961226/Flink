package com.example.flink.tools.data;

import androidx.annotation.Nullable;

import com.example.flink.mInterface.DataInterface;
import com.example.flink.mInterface.VisitDataCallBack;

import java.util.HashMap;

/**
 * 内存存储器：将数据存在内存中，生命周期为整个软件生命周期
 */
public class RamDataManager implements DataInterface {
    private static RamDataManager instance;

    //存放变量的map
    private HashMap<String, Object> globalVariableMap;

    private RamDataManager() {
        globalVariableMap = new HashMap<>();
    }

    static RamDataManager getInstance() {
        synchronized (RamDataManager.class) {
            if (instance == null) {
                instance = new RamDataManager();
            }
        }
        return instance;
    }


    @Override
    public void saveInteger(String key, int value, VisitDataCallBack<Integer> visitDataCallBack) {
        globalVariableMap.put(key, value);
    }

    @Override
    public void saveInteger(String key, int value) {
        saveInteger(key, value, null);
    }

    @Override
    public void saveDouble(String key, double value, VisitDataCallBack<Integer> visitDataCallBack) {
        globalVariableMap.put(key, value);
    }

    @Override
    public void saveDouble(String key, double value) {
        saveDouble(key, value, null);
    }

    @Override
    public void saveFloat(String key, float value, VisitDataCallBack<Integer> visitDataCallBack) {
        globalVariableMap.put(key, value);
    }

    @Override
    public void saveFloat(String key, float value) {
        saveFloat(key, value, null);
    }

    @Override
    public void saveString(String key, String value, VisitDataCallBack<Integer> visitDataCallBack) {
        globalVariableMap.put(key, value);
    }

    @Override
    public void saveString(String key, String value) {
        saveString(key, value, null);
    }

    @Override
    public void saveBoolean(String key, boolean value, VisitDataCallBack<Integer> visitDataCallBack) {
        globalVariableMap.put(key, value);
    }

    @Override
    public void saveBoolean(String key, boolean value) {
        saveBoolean(key, value, null);
    }

    @Override
    public void saveObject(String key, Object object, VisitDataCallBack<Integer> visitDataCallBack) {
        globalVariableMap.put(key, object);
    }

    @Override
    public void saveObject(String key, Object object) {
        saveObject(key, object, null);
    }

    @Override
    public int getInteger(String key, VisitDataCallBack<Integer> visitDataCallBack) {
        if (!globalVariableMap.containsKey(key)) {
            return 0;
        }
        return (int) globalVariableMap.get(key);
    }

    @Override
    public int getInteger(String key) {
        return getInteger(key, null);
    }

    @Override
    public double getDouble(String key, VisitDataCallBack<Integer> visitDataCallBack) {
        if (!globalVariableMap.containsKey(key)) {
            return 0;
        }
        return (double) globalVariableMap.get(key);
    }

    @Override
    public double getDouble(String key) {
        return getDouble(key, null);
    }

    @Override
    public float getFloat(String key, VisitDataCallBack<Integer> visitDataCallBack) {
        if (!globalVariableMap.containsKey(key)) {
            return 0;
        }
        return (float) globalVariableMap.get(key);
    }

    @Override
    public float getFloat(String key) {
        return getFloat(key, null);
    }

    @Override
    public String getString(String key, VisitDataCallBack<Integer> visitDataCallBack) {
        if (!globalVariableMap.containsKey(key)) {
            return "";
        }
        return (String) globalVariableMap.get(key);
    }

    @Override
    public String getString(String key) {
        return getString(key, null);
    }

    @Override
    public boolean getBoolean(String key, VisitDataCallBack<Integer> visitDataCallBack) {
        if (!globalVariableMap.containsKey(key)) {
            return false;
        }
        return (boolean) globalVariableMap.get(key);
    }

    @Override
    public boolean getBoolean(String key) {
        return getBoolean(key, null);
    }

    @Override
    @Nullable
    public Object getObject(String key, VisitDataCallBack<Integer> visitDataCallBack) {
        if (!globalVariableMap.containsKey(key)) {
            return null;
        }
        return globalVariableMap.get(key);
    }

    @Override
    @Nullable
    public Object getObject(String key) {
        return getObject(key, null);
    }
}
