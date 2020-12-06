package com.example.flink.tools.data;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.example.flink.mInterface.DataInterface;
import com.example.flink.mInterface.VisitDataCallBack;

import java.lang.ref.WeakReference;

import static android.content.Context.MODE_PRIVATE;

public class SpDataManager implements DataInterface {

    private static SpDataManager instance;
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    private SpDataManager(Context context) {
        WeakReference<Context> reference = new WeakReference<>(context);//防止内存泄漏
        sharedPreferences = reference.get().getSharedPreferences("user", MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static SpDataManager getInstance(Context context) {
        synchronized (SpDataManager.class) {
            if (instance == null) {
                instance = new SpDataManager(context);
            }
        }
        return instance;
    }

    @Override
    public void saveInteger(String key, int value, VisitDataCallBack<Integer> visitDataCallBack) {
        editor.putInt(key, value);
        editor.apply();
    }

    @Override
    public void saveInteger(String key, int value) {
        saveInteger(key, value, null);
    }

    @Override
    public void saveDouble(String key, double value, VisitDataCallBack<Integer> visitDataCallBack) {
        editor.putString(key, value + "");
        editor.apply();
    }

    @Override
    public void saveDouble(String key, double value) {
        saveDouble(key, value, null);
    }

    @Override
    public void saveFloat(String key, float value, VisitDataCallBack<Integer> visitDataCallBack) {
        editor.putFloat(key, value);
        editor.apply();
    }

    @Override
    public void saveFloat(String key, float value) {
        saveFloat(key, value, null);
    }

    @Override
    public void saveString(String key, String value, VisitDataCallBack<Integer> visitDataCallBack) {
        editor.putString(key, value);
        editor.apply();
    }

    @Override
    public void saveString(String key, String value) {
        saveString(key, value, null);
    }

    @Override
    public void saveBoolean(String key, boolean value, VisitDataCallBack<Integer> visitDataCallBack) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    @Override
    public void saveBoolean(String key, boolean value) {
        saveBoolean(key, value, null);
    }

    @Override
    public void saveObject(String key, Object object, VisitDataCallBack<Integer> visitDataCallBack) {
        //do nothing
    }

    @Override
    public void saveObject(String key, Object object) {
        //do nothing
    }

    @Override
    public int getInteger(String key, VisitDataCallBack<Integer> visitDataCallBack) {
        return sharedPreferences.getInt(key, 0);
    }

    @Override
    public int getInteger(String key) {
        return getInteger(key, null);
    }

    @Override
    public double getDouble(String key, VisitDataCallBack<Integer> visitDataCallBack) {
        return Double.parseDouble(sharedPreferences.getString(key, "0"));
    }

    @Override
    public double getDouble(String key) {
        return getDouble(key, null);
    }

    @Override
    public float getFloat(String key, VisitDataCallBack<Integer> visitDataCallBack) {
        return sharedPreferences.getFloat(key, 0);
    }

    @Override
    public float getFloat(String key) {
        return getFloat(key, null);
    }

    @Override
    public String getString(String key, VisitDataCallBack<Integer> visitDataCallBack) {
        return sharedPreferences.getString(key, "");
    }

    @Override
    public String getString(String key) {
        return getString(key, null);
    }

    @Override
    public boolean getBoolean(String key, VisitDataCallBack<Integer> visitDataCallBack) {
        return sharedPreferences.getBoolean(key, false);
    }

    @Override
    public boolean getBoolean(String key) {
        return getBoolean(key, null);
    }

    @Override
    @Nullable
    public Object getObject(String key, VisitDataCallBack<Integer> visitDataCallBack) {
        return null;
    }

    @Override
    @Nullable
    public Object getObject(String key) {
        return null;
    }
}
