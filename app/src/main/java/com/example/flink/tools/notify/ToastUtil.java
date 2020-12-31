package com.example.flink.tools.notify;


import android.app.Activity;
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

    private static void show(Activity activity, String tip) {
        if (activity == null) {
            throw new RuntimeException("activity is null,do not show toast on null activity !");
        }

        if (mToast == null) {
            mToast = Toast.makeText(activity, "", Toast.LENGTH_SHORT);
        }

        mToast.setText(tip);
        mToast.show();
    }
}
