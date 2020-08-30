package com.example.flink.common;

import android.Manifest;

public class MyConstants {

    private MyConstants() {
    }

    //需要申请的权限（注意：请同时在Manifast文件中写入）
    public static final String[] NEED_PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

}
