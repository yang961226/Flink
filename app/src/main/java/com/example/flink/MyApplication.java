package com.example.flink;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.example.flink.tools.greendao.GreenDaoManager;

/**
 * MyApplication
 */
public class MyApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initGreenDao();
    }

    public static Context getContext() {
        return context;
    }

    private void initGreenDao() {
        GreenDaoManager.getInstance(this);
        GreenDaoManager.getDaoMaster(this);
    }
}