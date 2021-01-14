package com.example.flink.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;

import com.example.flink.FlinkApplication;
import com.example.flink.FlinkBaseActivity;
import com.example.flink.R;

import java.util.Timer;
import java.util.TimerTask;


/**
 * 这个工具类存放通用的方法
 */
public class CommonTools {

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
        redirectDelay(from, to, null, delaySec, false);
    }

    public static void redirectDelay(Activity from, Class<? extends FlinkBaseActivity> to, int delaySec, boolean isKillSelf) {
        redirectDelay(from, to, null, (long) delaySec * 1000, isKillSelf);
    }

    public static void redirectDelay(Activity from, Class<? extends FlinkBaseActivity> to, long delayMs, boolean isKillSelf) {
        redirectDelay(from, to, null, delayMs, isKillSelf);
    }

    public static void redirectDelay(Activity from, Class<? extends FlinkBaseActivity> to, long delayMs) {
        redirectDelay(from, to, null, delayMs, false);
    }

    public static void redirectDelay(Activity from, Class<? extends FlinkBaseActivity> to, Bundle bundle, long delayMs) {
        redirectDelay(from, to, bundle, delayMs, false);
    }

    public static void redirectDelay(Activity from, Class<? extends FlinkBaseActivity> to, Bundle bundle, long delayMs, boolean isKillSelf) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                redirect(from, to, bundle);
                timer.cancel();
                from.finish();
                if(isKillSelf){
                    //activity管理
                    ActivityControl.getInstance().removeActivity(from);
                }
            }
        }, delayMs);
    }

    public static String getString(Context context,int strId){
        return context.getResources().getString(strId);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    // 将px值转换为sp值
    public static int dp2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public int px2sp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 是否是debug
     *
     * @return true:是debug
     */
    public static boolean isDebugMode() {
        ApplicationInfo info = FlinkApplication.getContext().getApplicationInfo();
        int debug = info.flags & ApplicationInfo.FLAG_DEBUGGABLE;
        return debug != 0;
    }

}
