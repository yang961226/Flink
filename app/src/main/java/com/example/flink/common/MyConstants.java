package com.example.flink.common;

import android.Manifest;

public class MyConstants {

    private MyConstants() {
        //屏蔽
    }

    //需要申请的权限（注意：请同时在Manifest文件中写入）
    public static final String[] NEED_PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    public static final String UNBIND_LISTENER = "该按钮未配置点击事件，请联系管理员";
    public static final String CLASS_CONFIG_ERROR = "类文件配置错误，请联系管理员";


    //存储Key值，必须KEY_开头
    public static final String KEY_NOW_DATE = "key_now_date";

    public static final String ENABLE_24HOURS_FORMAT = "enable_24hours_format";

    //okhttp拦截器使用常量
    public static final String CONNECT_TIMEOUT_FLAG = "connectTimeoutFlag";
    public static final String READ_TIMEOUT_FLAG = "readTimeoutFlag";
    public static final String WRITE_TIMEOUT_FLAG = "writeTimeoutFlag";
}
