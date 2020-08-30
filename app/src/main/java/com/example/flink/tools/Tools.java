package com.example.flink.tools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.flink.FlinkBaseActivity;
import com.example.flink.R;

import java.util.Timer;
import java.util.TimerTask;


/**
 * 这个工具类存放通用的方法
 */
public class Tools {

    /**
     * 带参数跳转
     *
     * @param from   起始activity
     * @param to     终点activity
     * @param bundle 参数
     */
    public static void redirect(Activity from, Class<? extends FlinkBaseActivity> to, Bundle bundle) {
        Intent intent = new Intent(from, to);
        if (bundle != null)
            intent.putExtras(bundle);
        from.startActivity(intent);
        from.overridePendingTransition(R.anim.view_push_left_in, R.anim.view_push_left_out);
    }

    public static void redirect(Activity from, Class<? extends FlinkBaseActivity> to) {
        redirect(from, to, new Bundle());
    }

    /**
     * 带返回结果的跳转
     *
     * @param from        起始activity
     * @param to          终点activity
     * @param bundle      参数
     * @param requestCode 请求标识码
     */
    public static void redirectForResult(Activity from, Class<? extends FlinkBaseActivity> to, Bundle bundle, int requestCode) {
        Intent intent = new Intent(from, to);
        if (bundle != null)
            intent.putExtras(bundle);
        from.startActivityForResult(intent, requestCode);
        from.overridePendingTransition(R.anim.view_push_left_in, R.anim.view_push_left_out);

    }

    public static void redirectForResult(Activity from, Class<? extends FlinkBaseActivity> to, int requestCode) {
        redirectForResult(from, to, new Bundle(), requestCode);
    }

    /**
     * 延时跳转
     *
     * @param from     起点
     * @param to       终点
     * @param bundle   参数
     * @param delaySec 延时（秒）
     */
    public static void redirectDelay(Activity from, Class<? extends FlinkBaseActivity> to, Bundle bundle, int delaySec) {
        redirectDelay(from, to, bundle, (long) delaySec * 1000);
    }

    public static void redirectDelay(Activity from, Class<? extends FlinkBaseActivity> to, int delaySec) {
        redirectDelay(from, to, new Bundle(), (long) delaySec * 1000);
    }

    public static void redirectDelay(Activity from, Class<? extends FlinkBaseActivity> to, Bundle bundle, long delayMs) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                redirect(from, to, bundle);
                timer.cancel();
            }
        }, delayMs);
    }
}
