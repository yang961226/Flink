package com.example.flink.tools.notify;

import android.util.Log;

import com.example.flink.tools.CommonTools;


public class LogUtil {

    private static final String TAG = "LogUtil:";

    public static void e(String tip) {
        e("", tip);
    }

    public static void e(String tag, String tip) {
        if (CommonTools.isDebugMode()) {
            Log.e(TAG + tag, "错误：" + tip);
        }
    }

    public static void d(String tip) {
        d("", tip);
    }

    public static void d(String tag, String tip) {
        if (CommonTools.isDebugMode()) {
            Log.d(TAG + tag, "调试：" + tip);
        }
    }

    public static void w(String tip) {
        w("", tip);
    }

    public static void w(String tag, String tip) {
        if (CommonTools.isDebugMode()) {
            Log.w(TAG + tag, "警告：" + tip);
        }
    }
}
