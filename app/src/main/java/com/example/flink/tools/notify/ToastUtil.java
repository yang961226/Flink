package com.example.flink.tools.notify;


import android.content.Context;
import android.widget.Toast;

import com.example.flink.tools.CommonTools;

public class ToastUtil {
    private static Toast mToast;

    public static void show(Context context, String text) {
        show(context, text, false);
    }

    public static void show(Context context, String text, boolean onlyShowInDebugMode) {
        if (context == null) {
            throw new RuntimeException("context is null,do not show toast on null activity !");
        }
        if (onlyShowInDebugMode && !CommonTools.isDebugMode()) {
            return;
        }
        if (mToast != null) mToast.cancel();
        mToast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
        mToast.setText(text);
        mToast.show();
    }
}
