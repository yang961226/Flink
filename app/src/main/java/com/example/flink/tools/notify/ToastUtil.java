package com.example.flink.tools.notify;


import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.widget.Toast;

import com.example.flink.FlinkApplication;

public class ToastUtil {
    private static Toast mToast;

    public static void showWhenDebug(Activity activity, String tip) {
        if (isDebug()) {
            show(activity, tip);
        }
    }

    public static void showWhenRelease(Activity activity, String tip) {
        if (!isDebug()) {
            show(activity, tip);
        }
    }

    private static boolean isDebug() {
        ApplicationInfo info = FlinkApplication.getContext().getApplicationInfo();
        int debug = info.flags & ApplicationInfo.FLAG_DEBUGGABLE;
        return debug != 0;
    }

    public static void show(Context context, String text) {
        if (context == null) {
            throw new RuntimeException("context is null,do not show toast on null activity !");
        }
        if (mToast != null) mToast.cancel();
        mToast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
        mToast.setText(text);
        mToast.show();
    }
}
