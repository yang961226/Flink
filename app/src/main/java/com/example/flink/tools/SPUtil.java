package com.example.flink.tools;

import android.content.Context;
import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * 数据保存类
 */
public class SPUtil {
    private static SPUtil instance;
    private static final String TAG = "SPUtil";
    private SharedPreferences.Editor editor;
    private WeakReference<Context> reference;

    private SPUtil(Context context) {
        reference=new WeakReference<>(context);//防止内存泄漏
        editor = reference.get().getSharedPreferences("user", MODE_PRIVATE).edit();
    }

    public static SPUtil getInstance(Context context) {
        synchronized (SPUtil.class) {
            if (instance == null) {
                instance = new SPUtil(context);
            }
        }

        return instance;
    }

    public SPUtil save(Map<String, Object> value) {
        for (Map.Entry<String, Object> vo : value.entrySet()) {
            Log.i(TAG, "saveSPData: " + vo.getValue().getClass().toString());

            System.out.println(vo.getValue().getClass().toString());

            switch (vo.getValue().getClass().toString()) {
                //整型
                case "class java.lang.Integer":
                    editor.putInt(vo.getKey(),
                            Integer.parseInt(vo.getValue().toString()));

                    break;

                //布尔型
                case "class java.lang.Boolean":
                    editor.putBoolean(vo.getKey(),
                            Boolean.parseBoolean(vo.getValue().toString()));

                    break;

                //字符串型
                case "class java.lang.String":
                    editor.putString(vo.getKey(), vo.getValue().toString());

                    break;

                //浮点型
                case "class java.lang.Float":
                    editor.putFloat(vo.getKey(),
                            Float.parseFloat(vo.getValue().toString()));

                    break;
            }
        }

        return instance;
    }

    public void save() {
        editor.apply();
    }
}
