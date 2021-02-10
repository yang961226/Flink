package com.example.flink;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.example.flink.common.MyConstants;
import com.example.flink.tools.DateUtil;
import com.example.flink.tools.data.DataManager;
import com.example.flink.tools.greendao.GreenDaoManager;

/**
 * MyApplication
 */
public class FlinkApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    /**
     * 不要随便获取这个context
     *
     * @return
     */
    public static Context getContext() {
        return context;
    }

    private void initGreenDao() {
        GreenDaoManager.getInstance();//初始化
        GreenDaoManager.getDaoMaster();//初始化
    }

    private void init() {
        context = getApplicationContext();
        initGreenDao();
        //初始化程序的当前选择日期
        DataManager.getDataManager(DataManager.DataManagerEnum.RAM_DATA_MANAGER)
                .saveObject(MyConstants.KEY_NOW_DATE, DateUtil.getNowDate());
    }
}