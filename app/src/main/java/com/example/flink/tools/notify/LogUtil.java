package com.example.flink.tools.notify;

import android.util.Log;


public class LogUtil {
    private static boolean isEnable = true;

    private static final String TAG = "Flink";

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public static void e(String tip) {
        if (isEnable) {
            Log.e(TAG, "错误：" + tip);
        }
    }

    public static void d(String tip) {
        if (isEnable) {
            Log.d(TAG, "调试：" + tip);
        }
    }

    public static void w(String tip) {
        if (isEnable) {
            Log.w(TAG, "警告：" + tip);
        }
    }
}
