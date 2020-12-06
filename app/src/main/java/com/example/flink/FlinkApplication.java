package com.example.flink;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.example.flink.common.MyConstants;
import com.example.flink.tools.DateUtil;
import com.example.flink.tools.greendao.GreenDaoManager;

import java.util.HashMap;

/**
 * MyApplication
 */
public class FlinkApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    //存放全局变量的map
    private static HashMap<String, Object> GLOBAL_VARIABLE_MAP;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    public static Context getContext() {
        return context;
    }

    private void initGreenDao() {
        GreenDaoManager.getInstance(this);
        GreenDaoManager.getDaoMaster(this);
    }

    public HashMap<String, Object> getGlobalVariableMap() {
        synchronized (FlinkApplication.class) {
            if (GLOBAL_VARIABLE_MAP == null) {
                GLOBAL_VARIABLE_MAP = new HashMap<>();
            }
        }
        return GLOBAL_VARIABLE_MAP;
    }

    private void init() {
        context = getApplicationContext();
        initGreenDao();

        //初始化程序的当前选择日期
        getGlobalVariableMap().put(MyConstants.KEY_NOW_DATE, DateUtil.getNowDate());
    }
}