package com.example.flink.tools.data;

import android.content.Context;

import androidx.annotation.Nullable;

import com.example.flink.FlinkApplication;
import com.example.flink.mInterface.DataInterface;
import com.example.flink.mInterface.VisitDataCallBack;

/**
 * 内存存储器：将数据存在内存中，生命周期为整个软件生命周期
 */
public class RamDataManager implements DataInterface {
    private static RamDataManager instance;
    private FlinkApplication flinkApplication;

    private RamDataManager(Context context) {
        flinkApplication = (FlinkApplication) context.getApplicationContext();
    }

    public static RamDataManager getInstance(Context context) {
        synchronized (RamDataManager.class) {
            if (instance == null) {
                instance = new RamDataManager(context);
            }
        }
        return instance;
    }


    @Override
    public void saveInteger(String key, int value, VisitDataCallBack<Integer> visitDataCallBack) {
        flinkApplication.getGlobalVariableMap().put(key, value);
    }

    @Override
    public void saveInteger(String key, int value) {
        saveInteger(key, value, null);
    }

    @Override
    public void saveDouble(String key, double value, VisitDataCallBack<Integer> visitDataCallBack) {
        flinkApplication.getGlobalVariableMap().put(key, value);
    }

    @Override
    public void saveDouble(String key, double value) {
        saveDouble(key, value, null);
    }

    @Override
    public void saveFloat(String key, float value, VisitDataCallBack<Integer> visitDataCallBack) {
        flinkApplication.getGlobalVariableMap().put(key, value);
    }

    @Override
    public void saveFloat(String key, float value) {
        saveFloat(key, value, null);
    }

    @Override
    public void saveString(String key, String value, VisitDataCallBack<Integer> visitDataCallBack) {
        flinkApplication.getGlobalVariableMap().put(key, value);
    }

    @Override
    public void saveString(String key, String value) {
        saveString(key, value, null);
    }

    @Override
    public void saveBoolean(String key, boolean value, VisitDataCallBack<Integer> visitDataCallBack) {
        flinkApplication.getGlobalVariableMap().put(key, value);
    }

    @Override
    public void saveBoolean(String key, boolean value) {
        saveBoolean(key, value, null);
    }

    @Override
    public void saveObject(String key, Object object, VisitDataCallBack<Integer> visitDataCallBack) {
        flinkApplication.getGlobalVariableMap().put(key, object);
    }

    @Override
    public void saveObject(String key, Object object) {
        saveObject(key, object, null);
    }

    @Override
    public int getInteger(String key, VisitDataCallBack<Integer> visitDataCallBack) {
        if (!flinkApplication.getGlobalVariableMap().containsKey(key)) {
            return 0;
        }
        return (int) flinkApplication.getGlobalVariableMap().get(key);
    }

    @Override
    public int getInteger(String key) {
        return getInteger(key, null);
    }

    @Override
    public double getDouble(String key, VisitDataCallBack<Integer> visitDataCallBack) {
        if (!flinkApplication.getGlobalVariableMap().containsKey(key)) {
            return 0;
        }
        return (double) flinkApplication.getGlobalVariableMap().get(key);
    }

    @Override
    public double getDouble(String key) {
        return getDouble(key, null);
    }

    @Override
    public float getFloat(String key, VisitDataCallBack<Integer> visitDataCallBack) {
        if (!flinkApplication.getGlobalVariableMap().containsKey(key)) {
            return 0;
        }
        return (float) flinkApplication.getGlobalVariableMap().get(key);
    }

    @Override
    public float getFloat(String key) {
        return getFloat(key, null);
    }

    @Override
    public String getString(String key, VisitDataCallBack<Integer> visitDataCallBack) {
        if (!flinkApplication.getGlobalVariableMap().containsKey(key)) {
            return "";
        }
        return (String) flinkApplication.getGlobalVariableMap().get(key);
    }

    @Override
    public String getString(String key) {
        return getString(key, null);
    }

    @Override
    public boolean getBoolean(String key, VisitDataCallBack<Integer> visitDataCallBack) {
        if (!flinkApplication.getGlobalVariableMap().containsKey(key)) {
            return false;
        }
        return (boolean) flinkApplication.getGlobalVariableMap().get(key);
    }

    @Override
    public boolean getBoolean(String key) {
        return getBoolean(key, null);
    }

    @Override
    @Nullable
    public Object getObject(String key, VisitDataCallBack<Integer> visitDataCallBack) {
        if (!flinkApplication.getGlobalVariableMap().containsKey(key)) {
            return null;
        }
        return flinkApplication.getGlobalVariableMap().get(key);
    }

    @Override
    @Nullable
    public Object getObject(String key) {
        return getObject(key, null);
    }
}
