package com.example.flink.common;

import android.Manifest;

public class MyConstants {

    private MyConstants() {
        //屏蔽
    }

    //需要申请的权限（注意：请同时在Manifast文件中写入）
    public static final String[] NEED_PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    public static final String CLICK_BTN_TODAY="点击今天按钮";
    public static final String CLICK_BTN_LAST_DAY="点击上一天按钮";
    public static final String CLICK_BTN_LAST_MONTH="点击上一个月按钮";
    public static final String CLICK_BTN_NEXT_DAY="点击下一天按钮";
    public static final String CLICK_BTN_NEXT_MONTH="点击下一个月按钮";

    public static final String SELECT_DATE="选择日期";

    public static final String UNBIND_LISTENER="该按钮未配置点击事件，请联系管理员";
    public static final String CLASS_CONFIG_ERROR="类文件配置错误，请联系管理员";
}
