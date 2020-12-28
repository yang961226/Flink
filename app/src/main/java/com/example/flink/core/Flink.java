package com.example.flink.core;


import android.app.Application;
import android.content.Context;

public class Flink {
    private static Flink instance = new Flink();
    private Application mApplication;
    private Context mContext;

    private Flink() {

    }

    public void init(Application application) {
        mApplication = application;
        mContext = application.getApplicationContext();
    }

    public static Flink getInstance() {
        if (instance == null) {
            instance = new Flink();
        }

        return instance;
    }

    public Application getApplication() {
        return mApplication;
    }

    public Context getContext() {
        return mContext;
    }
}
