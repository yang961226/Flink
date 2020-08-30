package com.example.flink.item;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * activity处理工具
 */
public class ActivityControl {
    private String TAG = ActivityControl.class.getName();
    private List<Activity> mActivityList = new ArrayList<>();
    private static ActivityControl mActivityControl;

    public static synchronized ActivityControl getInstance() {
        if (null == mActivityControl) {
            mActivityControl = new ActivityControl();
        }
        return mActivityControl;
    }

    public void addActivity(Activity activity) {
        if (mActivityControl == null) {
            mActivityControl = new ActivityControl();
        }
        mActivityList.add(activity);
        Log.d("TAG", "activity处理工具:添加" + activity.getClass().getName());
    }

    public void removeActivity(Activity activity) {
        if (mActivityList != null) {
            mActivityList.remove(activity);
        }
        Log.d("TAG", "activity处理工具:删除" + activity.getClass().getName());
    }

    private void clearAllActivity() {
        if (mActivityList != null) {
            for (Activity activity : mActivityList) {
                if (activity != null) {
                    activity.finish();
                }
            }
            Log.d("TAG", "activity处理工具:清空所有Activity");
        }
    }
}
